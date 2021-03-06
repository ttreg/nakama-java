// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api.proto

package com.heroiclabs.nakama.api;

/**
 * <pre>
 * Import Facebook friends into the current user's account.
 * </pre>
 *
 * Protobuf type {@code nakama.api.ImportFacebookFriendsRequest}
 */
public  final class ImportFacebookFriendsRequest extends
    com.google.protobuf.GeneratedMessageLite<
        ImportFacebookFriendsRequest, ImportFacebookFriendsRequest.Builder> implements
    // @@protoc_insertion_point(message_implements:nakama.api.ImportFacebookFriendsRequest)
    ImportFacebookFriendsRequestOrBuilder {
  private ImportFacebookFriendsRequest() {
  }
  public static final int ACCOUNT_FIELD_NUMBER = 1;
  private com.heroiclabs.nakama.api.AccountFacebook account_;
  /**
   * <pre>
   * The Facebook account details.
   * </pre>
   *
   * <code>optional .nakama.api.AccountFacebook account = 1;</code>
   */
  public boolean hasAccount() {
    return account_ != null;
  }
  /**
   * <pre>
   * The Facebook account details.
   * </pre>
   *
   * <code>optional .nakama.api.AccountFacebook account = 1;</code>
   */
  public com.heroiclabs.nakama.api.AccountFacebook getAccount() {
    return account_ == null ? com.heroiclabs.nakama.api.AccountFacebook.getDefaultInstance() : account_;
  }
  /**
   * <pre>
   * The Facebook account details.
   * </pre>
   *
   * <code>optional .nakama.api.AccountFacebook account = 1;</code>
   */
  private void setAccount(com.heroiclabs.nakama.api.AccountFacebook value) {
    if (value == null) {
      throw new NullPointerException();
    }
    account_ = value;
    
    }
  /**
   * <pre>
   * The Facebook account details.
   * </pre>
   *
   * <code>optional .nakama.api.AccountFacebook account = 1;</code>
   */
  private void setAccount(
      com.heroiclabs.nakama.api.AccountFacebook.Builder builderForValue) {
    account_ = builderForValue.build();
    
  }
  /**
   * <pre>
   * The Facebook account details.
   * </pre>
   *
   * <code>optional .nakama.api.AccountFacebook account = 1;</code>
   */
  private void mergeAccount(com.heroiclabs.nakama.api.AccountFacebook value) {
    if (account_ != null &&
        account_ != com.heroiclabs.nakama.api.AccountFacebook.getDefaultInstance()) {
      account_ =
        com.heroiclabs.nakama.api.AccountFacebook.newBuilder(account_).mergeFrom(value).buildPartial();
    } else {
      account_ = value;
    }
    
  }
  /**
   * <pre>
   * The Facebook account details.
   * </pre>
   *
   * <code>optional .nakama.api.AccountFacebook account = 1;</code>
   */
  private void clearAccount() {  account_ = null;
    
  }

  public static final int RESET_FIELD_NUMBER = 2;
  private com.google.protobuf.BoolValue reset_;
  /**
   * <pre>
   * Reset the current user's friends list.
   * </pre>
   *
   * <code>optional .google.protobuf.BoolValue reset = 2;</code>
   */
  public boolean hasReset() {
    return reset_ != null;
  }
  /**
   * <pre>
   * Reset the current user's friends list.
   * </pre>
   *
   * <code>optional .google.protobuf.BoolValue reset = 2;</code>
   */
  public com.google.protobuf.BoolValue getReset() {
    return reset_ == null ? com.google.protobuf.BoolValue.getDefaultInstance() : reset_;
  }
  /**
   * <pre>
   * Reset the current user's friends list.
   * </pre>
   *
   * <code>optional .google.protobuf.BoolValue reset = 2;</code>
   */
  private void setReset(com.google.protobuf.BoolValue value) {
    if (value == null) {
      throw new NullPointerException();
    }
    reset_ = value;
    
    }
  /**
   * <pre>
   * Reset the current user's friends list.
   * </pre>
   *
   * <code>optional .google.protobuf.BoolValue reset = 2;</code>
   */
  private void setReset(
      com.google.protobuf.BoolValue.Builder builderForValue) {
    reset_ = builderForValue.build();
    
  }
  /**
   * <pre>
   * Reset the current user's friends list.
   * </pre>
   *
   * <code>optional .google.protobuf.BoolValue reset = 2;</code>
   */
  private void mergeReset(com.google.protobuf.BoolValue value) {
    if (reset_ != null &&
        reset_ != com.google.protobuf.BoolValue.getDefaultInstance()) {
      reset_ =
        com.google.protobuf.BoolValue.newBuilder(reset_).mergeFrom(value).buildPartial();
    } else {
      reset_ = value;
    }
    
  }
  /**
   * <pre>
   * Reset the current user's friends list.
   * </pre>
   *
   * <code>optional .google.protobuf.BoolValue reset = 2;</code>
   */
  private void clearReset() {  reset_ = null;
    
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (account_ != null) {
      output.writeMessage(1, getAccount());
    }
    if (reset_ != null) {
      output.writeMessage(2, getReset());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (account_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getAccount());
    }
    if (reset_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getReset());
    }
    memoizedSerializedSize = size;
    return size;
  }

  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.heroiclabs.nakama.api.ImportFacebookFriendsRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * <pre>
   * Import Facebook friends into the current user's account.
   * </pre>
   *
   * Protobuf type {@code nakama.api.ImportFacebookFriendsRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.heroiclabs.nakama.api.ImportFacebookFriendsRequest, Builder> implements
      // @@protoc_insertion_point(builder_implements:nakama.api.ImportFacebookFriendsRequest)
      com.heroiclabs.nakama.api.ImportFacebookFriendsRequestOrBuilder {
    // Construct using com.heroiclabs.nakama.api.ImportFacebookFriendsRequest.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <pre>
     * The Facebook account details.
     * </pre>
     *
     * <code>optional .nakama.api.AccountFacebook account = 1;</code>
     */
    public boolean hasAccount() {
      return instance.hasAccount();
    }
    /**
     * <pre>
     * The Facebook account details.
     * </pre>
     *
     * <code>optional .nakama.api.AccountFacebook account = 1;</code>
     */
    public com.heroiclabs.nakama.api.AccountFacebook getAccount() {
      return instance.getAccount();
    }
    /**
     * <pre>
     * The Facebook account details.
     * </pre>
     *
     * <code>optional .nakama.api.AccountFacebook account = 1;</code>
     */
    public Builder setAccount(com.heroiclabs.nakama.api.AccountFacebook value) {
      copyOnWrite();
      instance.setAccount(value);
      return this;
      }
    /**
     * <pre>
     * The Facebook account details.
     * </pre>
     *
     * <code>optional .nakama.api.AccountFacebook account = 1;</code>
     */
    public Builder setAccount(
        com.heroiclabs.nakama.api.AccountFacebook.Builder builderForValue) {
      copyOnWrite();
      instance.setAccount(builderForValue);
      return this;
    }
    /**
     * <pre>
     * The Facebook account details.
     * </pre>
     *
     * <code>optional .nakama.api.AccountFacebook account = 1;</code>
     */
    public Builder mergeAccount(com.heroiclabs.nakama.api.AccountFacebook value) {
      copyOnWrite();
      instance.mergeAccount(value);
      return this;
    }
    /**
     * <pre>
     * The Facebook account details.
     * </pre>
     *
     * <code>optional .nakama.api.AccountFacebook account = 1;</code>
     */
    public Builder clearAccount() {  copyOnWrite();
      instance.clearAccount();
      return this;
    }

    /**
     * <pre>
     * Reset the current user's friends list.
     * </pre>
     *
     * <code>optional .google.protobuf.BoolValue reset = 2;</code>
     */
    public boolean hasReset() {
      return instance.hasReset();
    }
    /**
     * <pre>
     * Reset the current user's friends list.
     * </pre>
     *
     * <code>optional .google.protobuf.BoolValue reset = 2;</code>
     */
    public com.google.protobuf.BoolValue getReset() {
      return instance.getReset();
    }
    /**
     * <pre>
     * Reset the current user's friends list.
     * </pre>
     *
     * <code>optional .google.protobuf.BoolValue reset = 2;</code>
     */
    public Builder setReset(com.google.protobuf.BoolValue value) {
      copyOnWrite();
      instance.setReset(value);
      return this;
      }
    /**
     * <pre>
     * Reset the current user's friends list.
     * </pre>
     *
     * <code>optional .google.protobuf.BoolValue reset = 2;</code>
     */
    public Builder setReset(
        com.google.protobuf.BoolValue.Builder builderForValue) {
      copyOnWrite();
      instance.setReset(builderForValue);
      return this;
    }
    /**
     * <pre>
     * Reset the current user's friends list.
     * </pre>
     *
     * <code>optional .google.protobuf.BoolValue reset = 2;</code>
     */
    public Builder mergeReset(com.google.protobuf.BoolValue value) {
      copyOnWrite();
      instance.mergeReset(value);
      return this;
    }
    /**
     * <pre>
     * Reset the current user's friends list.
     * </pre>
     *
     * <code>optional .google.protobuf.BoolValue reset = 2;</code>
     */
    public Builder clearReset() {  copyOnWrite();
      instance.clearReset();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:nakama.api.ImportFacebookFriendsRequest)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.heroiclabs.nakama.api.ImportFacebookFriendsRequest();
      }
      case IS_INITIALIZED: {
        return DEFAULT_INSTANCE;
      }
      case MAKE_IMMUTABLE: {
        return null;
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case VISIT: {
        Visitor visitor = (Visitor) arg0;
        com.heroiclabs.nakama.api.ImportFacebookFriendsRequest other = (com.heroiclabs.nakama.api.ImportFacebookFriendsRequest) arg1;
        account_ = visitor.visitMessage(account_, other.account_);
        reset_ = visitor.visitMessage(reset_, other.reset_);
        if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
            .INSTANCE) {
        }
        return this;
      }
      case MERGE_FROM_STREAM: {
        com.google.protobuf.CodedInputStream input =
            (com.google.protobuf.CodedInputStream) arg0;
        com.google.protobuf.ExtensionRegistryLite extensionRegistry =
            (com.google.protobuf.ExtensionRegistryLite) arg1;
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              default: {
                if (!input.skipField(tag)) {
                  done = true;
                }
                break;
              }
              case 10: {
                com.heroiclabs.nakama.api.AccountFacebook.Builder subBuilder = null;
                if (account_ != null) {
                  subBuilder = account_.toBuilder();
                }
                account_ = input.readMessage(com.heroiclabs.nakama.api.AccountFacebook.parser(), extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(account_);
                  account_ = subBuilder.buildPartial();
                }

                break;
              }
              case 18: {
                com.google.protobuf.BoolValue.Builder subBuilder = null;
                if (reset_ != null) {
                  subBuilder = reset_.toBuilder();
                }
                reset_ = input.readMessage(com.google.protobuf.BoolValue.parser(), extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(reset_);
                  reset_ = subBuilder.buildPartial();
                }

                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw new RuntimeException(e.setUnfinishedMessage(this));
        } catch (java.io.IOException e) {
          throw new RuntimeException(
              new com.google.protobuf.InvalidProtocolBufferException(
                  e.getMessage()).setUnfinishedMessage(this));
        } finally {
        }
      }
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        if (PARSER == null) {    synchronized (com.heroiclabs.nakama.api.ImportFacebookFriendsRequest.class) {
            if (PARSER == null) {
              PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
            }
          }
        }
        return PARSER;
      }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:nakama.api.ImportFacebookFriendsRequest)
  private static final com.heroiclabs.nakama.api.ImportFacebookFriendsRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ImportFacebookFriendsRequest();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static com.heroiclabs.nakama.api.ImportFacebookFriendsRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<ImportFacebookFriendsRequest> PARSER;

  public static com.google.protobuf.Parser<ImportFacebookFriendsRequest> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

