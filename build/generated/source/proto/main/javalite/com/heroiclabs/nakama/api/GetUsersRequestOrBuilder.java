// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api.proto

package com.heroiclabs.nakama.api;

public interface GetUsersRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:nakama.api.GetUsersRequest)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <pre>
   * The account id of a user.
   * </pre>
   *
   * <code>repeated string ids = 1;</code>
   */
  java.util.List<String>
      getIdsList();
  /**
   * <pre>
   * The account id of a user.
   * </pre>
   *
   * <code>repeated string ids = 1;</code>
   */
  int getIdsCount();
  /**
   * <pre>
   * The account id of a user.
   * </pre>
   *
   * <code>repeated string ids = 1;</code>
   */
  java.lang.String getIds(int index);
  /**
   * <pre>
   * The account id of a user.
   * </pre>
   *
   * <code>repeated string ids = 1;</code>
   */
  com.google.protobuf.ByteString
      getIdsBytes(int index);

  /**
   * <pre>
   * The account username of a user.
   * </pre>
   *
   * <code>repeated string usernames = 2;</code>
   */
  java.util.List<String>
      getUsernamesList();
  /**
   * <pre>
   * The account username of a user.
   * </pre>
   *
   * <code>repeated string usernames = 2;</code>
   */
  int getUsernamesCount();
  /**
   * <pre>
   * The account username of a user.
   * </pre>
   *
   * <code>repeated string usernames = 2;</code>
   */
  java.lang.String getUsernames(int index);
  /**
   * <pre>
   * The account username of a user.
   * </pre>
   *
   * <code>repeated string usernames = 2;</code>
   */
  com.google.protobuf.ByteString
      getUsernamesBytes(int index);

  /**
   * <pre>
   * The Facebook ID of a user.
   * </pre>
   *
   * <code>repeated string facebook_ids = 3;</code>
   */
  java.util.List<String>
      getFacebookIdsList();
  /**
   * <pre>
   * The Facebook ID of a user.
   * </pre>
   *
   * <code>repeated string facebook_ids = 3;</code>
   */
  int getFacebookIdsCount();
  /**
   * <pre>
   * The Facebook ID of a user.
   * </pre>
   *
   * <code>repeated string facebook_ids = 3;</code>
   */
  java.lang.String getFacebookIds(int index);
  /**
   * <pre>
   * The Facebook ID of a user.
   * </pre>
   *
   * <code>repeated string facebook_ids = 3;</code>
   */
  com.google.protobuf.ByteString
      getFacebookIdsBytes(int index);
}