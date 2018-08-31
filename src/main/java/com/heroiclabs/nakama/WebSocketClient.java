/*
 * Copyright 2018 The Nakama Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.heroiclabs.nakama;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int32Value;
import com.heroiclabs.nakama.api.NotificationList;
import com.heroiclabs.nakama.api.Rpc;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.ByteString;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebSocketClient implements SocketClient {
    static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private final String host;
    private final int port;
    private final boolean ssl;
    private final boolean trace;
    private final OkHttpClient client;
    private final Map<String, SettableFuture<?>> collationIds;
    private WebSocket socket;

    WebSocketClient(@NonNull final String host, @NonNull final int port, @NonNull final boolean ssl,
                    @NonNull final int socketTimeoutMs, @NonNull final boolean trace) {
        this.host = host;
        this.port = port;
        this.ssl = ssl;
        this.trace = trace;
        this.collationIds = new ConcurrentHashMap<>();

        client = new OkHttpClient.Builder()
                .connectTimeout(socketTimeoutMs, TimeUnit.MILLISECONDS)
                .readTimeout(socketTimeoutMs, TimeUnit.MILLISECONDS)
                .writeTimeout(socketTimeoutMs, TimeUnit.MILLISECONDS)
                .build();
    }

    public ListenableFuture<Session> connect(@NonNull final Session session, @NonNull final ClientListener listener) {
        if (socket != null) {
            return Futures.immediateFailedFuture(new DefaultError("Client is already connected"));
        }

        final String url = new HttpUrl.Builder()
                .scheme(ssl ? "https" : "http")
                .host(host)
                .port(port)
                .encodedPath("/ws")
                .addQueryParameter("token", session.getAuthToken())
                .build()
                .toString()
                .replaceFirst("http", "ws");

        final Request request = new Request.Builder()
                .url(url)
                .build();

        if (trace) {
            log.debug("Connect: " + request.toString());
        }

        return createWebsocket(session, listener, request);
    }

    private ListenableFuture<Session> createWebsocket(@NonNull final Session session, @NonNull final ClientListener listener, @NonNull final Request request) {
        final SettableFuture<Session> future = SettableFuture.create();
        final Object lock = this;
        socket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(final WebSocket webSocket, final Response response) {
                super.onOpen(webSocket, response);
                // Notify the deferred caller that the client has connected and is ready to use.
                future.set(session);
            }

            @Override
            public void onMessage(final WebSocket webSocket, final ByteString bytes) {
                super.onMessage(webSocket, bytes);
                // No text messages are expected.
                log.warn("Unexpected binary message from server: " + bytes.base64());
            }

            @Override
            public void onMessage(final WebSocket webSocket, final String text) {
                super.onMessage(webSocket, text);

                final WebSocketEnvelope env;
                try {
                    env = GSON.fromJson(text, WebSocketEnvelope.class);
                } catch (final JsonSyntaxException e) {
                    // TODO onError callback
                    log.error("Error decoding incoming message from server: " + e.getMessage());
                    return;
                }

                final String collationId = env.getCid();
                if (collationId == null || "".equals(collationId)) {
                    if (env.getChannelMessage() != null) {
                        final ChannelMessage m = env.getChannelMessage();

                        final com.heroiclabs.nakama.api.ChannelMessage result = com.heroiclabs.nakama.api.ChannelMessage.newBuilder()
                                .setMessageId(m.getMessageId())
                                .setSenderId(m.getSenderId())
                                .setUsername(m.getUsername())
                                .setChannelId(m.getChannelId())
                                .setContent(m.getContent())
                                .setPersistent(BoolValue.newBuilder().setValue(m.isPersistent()).getDefaultInstanceForType())
                                .setCode(Int32Value.newBuilder().setValue(m.getCode()).build())
//                                .setCreateTime(Timestamp.newBuilder().setSeconds(m.getCreateTime()).build())
//                                .setUpdateTime(Timestamp.newBuilder().setSeconds(m.getUpdateTime()).build())
                                .build();
                        listener.onChannelMessage(result);
                    } else if (env.getChannelPresenceEvent() != null) {
                        listener.onChannelPresence(env.getChannelPresenceEvent());
                    } else if (env.getMatchData() != null) {
                        listener.onMatchData(env.getMatchData());
                    } else if (env.getMatchPresenceEvent() != null) {
                        listener.onMatchPresence(env.getMatchPresenceEvent());
                    } else if (env.getMatchmakerMatched() != null) {
                        listener.onMatchmakeMatched(env.getMatchmakerMatched());
                    } else if (env.getNotifications() != null) {
                        final NotificationList.Builder resultBuilder = NotificationList.newBuilder();
                        resultBuilder.setCacheableCursor(env.getNotifications().getCacheableCursor());
                        for (final Notification n : env.getNotifications().getNotifications()) {
                            resultBuilder.addNotifications(com.heroiclabs.nakama.api.Notification.newBuilder()
                                    .setCode(n.getCode())
                                    .setContent(n.getContent())
//                                    .setCreateTime(Timestamp.newBuilder().setSeconds(n.getCreateTime()).build())
                                    .setId(n.getId())
                                    .setSenderId(n.getSenderId())
                                    .setSubject(n.getSubject())
                                    .build());
                        }
                        listener.onNotifications(resultBuilder.build());
                    } else if (env.getStatusPresenceEvent() != null) {
                        listener.onStatusPresence(env.getStatusPresenceEvent());
                    } else if (env.getStreamData() != null) {
                        listener.onStreamData(env.getStreamData());
                    } else if (env.getStreamPresenceEvent() != null) {
                        listener.onStreamPresence(env.getStreamPresenceEvent());
                    } else {
                        log.error("Unrecognised incoming uncollated message from server: " + env.toString());
                    }
                    return;
                }

                final SettableFuture future = collationIds.get(collationId);
                if (future == null) {
                    log.warn("No matching future for incoming collation ID: " + collationId);
                    return;
                }

                if (env.getError() != null) {
                    future.setException(new DefaultError(collationId, env.getError()));
                } else if (env.getRpc() != null) {
                    final com.heroiclabs.nakama.api.Rpc apiRpc = com.heroiclabs.nakama.api.Rpc.newBuilder()
                            .setId(env.getRpc().getId())
                            .setPayload(env.getRpc().getPayload())
                            .build();
                    future.set(apiRpc);
                } else if (env.getChannel() != null) {
                    future.set(env.getChannel());
                } else if (env.getChannelMessageAck() != null) {
                    future.set(env.getChannelMessageAck());
                } else if (env.getMatch() != null) {
                    future.set(env.getMatch());
                } else if (env.getMatchmakerTicket() != null) {
                    future.set(env.getMatchmakerTicket());
                } else if (env.getStatus() != null) {
                    future.set(env.getStatus());
                } else {
                    future.set(null);
                }
            }

            @Override
            public void onClosing(final WebSocket webSocket, final int code, final String reason) {
                super.onClosing(webSocket, code, reason);
                // No action needed here.
            }

            @Override
            public void onClosed(final WebSocket webSocket, final int code, final String reason) {
                super.onClosed(webSocket, code, reason);
                // Graceful socket disconnect is complete, clean up.
                synchronized (lock) {
                    socket = null;
                    // TODO callback any leftover deferred items with a disconnect error message?
                    collationIds.clear();
                }
                listener.onDisconnect();
            }

            @Override
            public void onFailure(final WebSocket webSocket, final Throwable t, final Response response) {
                super.onFailure(webSocket, t, response);
                // Socket has failed and is no longer connected, clean up.
                synchronized (lock) {
                    socket = null;
                    // TODO callback any leftover deferred items with a disconnect error message?
                    collationIds.clear();
                }
                listener.onDisconnect();
            }
        });
        return future;
    }

    @Override
    public synchronized ListenableFuture<Boolean> disconnect() {
        if (socket != null) {
            // Returns true if a shutdown was initiated, false if already shutting down or disconnected.
            // Either result is acceptable here.
            // Socket reference will be set to null when disconnect is completed.
            socket.close(1000, null);
            socket = null;
        }
        return Futures.immediateFuture(true);
    }

    @Override
    public ListenableFuture<Channel> channelJoin(@NonNull final String target, @NonNull final ChannelType type) {
        final ChannelJoinMessage msg = new ChannelJoinMessage(target, type.getValue());

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setChannelJoin(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Channel> channelJoin(@NonNull final String target, @NonNull final ChannelType type, @NonNull final boolean persistence) {
        final ChannelJoinMessage msg = new ChannelJoinMessage(target, type.getValue());
        msg.setPersistence(persistence);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setChannelJoin(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Channel> channelJoin(@NonNull final String target, @NonNull final ChannelType type, @NonNull final boolean persistence, @NonNull final boolean hidden) {
        final ChannelJoinMessage msg = new ChannelJoinMessage(target, type.getValue());
        msg.setPersistence(persistence);
        msg.setHidden(hidden);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setChannelJoin(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Void> channelLeave(@NonNull final String channelId) {
        final ChannelLeaveMessage msg = new ChannelLeaveMessage(channelId);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setChannelLeave(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<ChannelMessageAck> channelMessageRemove(@NonNull final String channelId, @NonNull final String messageId) {
        final ChannelRemoveMessage msg = new ChannelRemoveMessage(channelId, messageId);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setChannelRemoveMessage(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<ChannelMessageAck> channelMessageSend(@NonNull final String channelId, @NonNull final String content) {
        final ChannelSendMessage msg = new ChannelSendMessage(channelId, content);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setChannelMessageSend(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<ChannelMessageAck> channelMessageUpdate(@NonNull final String channelId, @NonNull final String messageId, @NonNull final String content) {
        final ChannelUpdateMessage msg = new ChannelUpdateMessage(channelId, messageId, content);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setChannelMessageUpdate(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Match> matchCreate() {
        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setMatchCreate(new MatchCreateMessage());
        return send(env);
    }

    @Override
    public ListenableFuture<Match> matchJoin(@NonNull final String matchId) {
        final MatchJoinMessage msg = new MatchJoinMessage();
        msg.setMatchId(matchId);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setMatchJoin(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Match> matchJoinToken(@NonNull final String token) {
        final MatchJoinMessage msg = new MatchJoinMessage();
        msg.setToken(token);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setMatchJoin(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Void> matchLeave(@NonNull final String matchId) {
        final MatchLeaveMessage msg = new MatchLeaveMessage(matchId);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setMatchLeave(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<MatchmakerTicket> matchmakerAdd() {
        return matchmakerAdd(0, 0, null, null, null);
    }

    @Override
    public ListenableFuture<MatchmakerTicket> matchmakerAdd(final int minCount) {
        return matchmakerAdd(minCount, 0, null, null, null);
    }

    @Override
    public ListenableFuture<MatchmakerTicket> matchmakerAdd(final int minCount, final int maxCount) {
        return matchmakerAdd(minCount, maxCount, null, null, null);
    }

    @Override
    public ListenableFuture<MatchmakerTicket> matchmakerAdd(final int minCount, final int maxCount, final String query) {
        return matchmakerAdd(minCount, maxCount, query, null, null);
    }

    @Override
    public ListenableFuture<MatchmakerTicket> matchmakerAdd(final int minCount, final int maxCount, final String query, final Map<String, String> stringProperties) {
        return matchmakerAdd(minCount, maxCount, query, stringProperties, null);
    }

    @Override
    public ListenableFuture<MatchmakerTicket> matchmakerAdd(final int minCount, final int maxCount, final String query, final Map<String, String> stringProperties, final Map<String, Double> numericProperties) {
        final MatchmakerAddMessage msg = new MatchmakerAddMessage();
        if (minCount > 0) {
            msg.setMinCount(minCount);
        }
        if (maxCount > 0) {
            msg.setMaxCount(maxCount);
        }
        if (query != null) {
            msg.setQuery(query);
        }
        if (stringProperties != null) {
            msg.setStringProperties(stringProperties);
        }
        if (numericProperties != null) {
            msg.setNumericProperties(numericProperties);
        }

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setMatchmakerAdd(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Void> matchmakerRemove(@NonNull final String ticket) {
        final MatchmakerRemoveMessage msg = new MatchmakerRemoveMessage(ticket);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setMatchmakerRemove(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Void> matchDataSend(@NonNull final String matchId, @NonNull final String opCode, @NonNull final String data) {
        return matchDataSend(matchId, opCode, data, (UserPresence) null);
    }

    @Override
    public ListenableFuture<Void> matchDataSend(@NonNull final String matchId, @NonNull final String opCode, @NonNull final String data, final UserPresence... presences) {
        final MatchSendMessage msg = new MatchSendMessage(matchId, opCode, data);
        if (presences != null) {
            msg.setPresences(Arrays.asList(presences));
        }

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setMatchDataSend(msg);
        return send(env);
    }

    @Override
    public ListenableFuture<Rpc> rpc(@NonNull final String id) {
        return rpc(id, null);
    }

    @Override
    public ListenableFuture<Rpc> rpc(@NonNull final String id, final String payload) {
        final RpcMessage message = new RpcMessage();
        message.setId(id);
        if (payload != null) {
            message.setPayload(payload);
        }

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setRpc(message);
        return send(env);
    }

    @Override
    public ListenableFuture<Status> statusFollow(@NonNull final String... userIds) {
        final StatusFollowMessage message = new StatusFollowMessage(Arrays.asList(userIds));

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setStatusFollow(message);
        return send(env);
    }

    @Override
    public ListenableFuture<Void> statusUnfollow(@NonNull final String... userIds) {
        final StatusUnfollowMessage message = new StatusUnfollowMessage(Arrays.asList(userIds));

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setStatusUnfollow(message);
        return send(env);
    }

    @Override
    public ListenableFuture<Void> statusUpdate(@NonNull final String status) {
        final StatusUpdateMessage message = new StatusUpdateMessage(status);

        final WebSocketEnvelope env = new WebSocketEnvelope();
        env.setStatusUpdate(message);
        return send(env);
    }

    private <T> ListenableFuture<T> send(@NonNull final WebSocketEnvelope webSocketEnvelope) {
        if (socket == null) {
            return Futures.immediateFailedFuture(new DefaultError("Socket is not connected."));
        }

        final SettableFuture<T> future = SettableFuture.create();
        final String collationId = UUID.randomUUID().toString();
        webSocketEnvelope.setCid(collationId);
        collationIds.put(collationId, future);

        final boolean enqueued = socket.send(GSON.toJson(webSocketEnvelope));
        if (!enqueued) {
            future.setException(new DefaultError("Could not enqueue message - is the socket connected?"));
        }
        return future;
    }
}