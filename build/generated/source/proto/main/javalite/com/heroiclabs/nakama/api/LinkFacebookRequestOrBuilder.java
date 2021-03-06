// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api.proto

package com.heroiclabs.nakama.api;

public interface LinkFacebookRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:nakama.api.LinkFacebookRequest)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <pre>
   * The Facebook account details.
   * </pre>
   *
   * <code>optional .nakama.api.AccountFacebook account = 1;</code>
   */
  boolean hasAccount();
  /**
   * <pre>
   * The Facebook account details.
   * </pre>
   *
   * <code>optional .nakama.api.AccountFacebook account = 1;</code>
   */
  com.heroiclabs.nakama.api.AccountFacebook getAccount();

  /**
   * <pre>
   * Import Facebook friends for the user.
   * </pre>
   *
   * <code>optional .google.protobuf.BoolValue import = 4;</code>
   */
  boolean hasImport();
  /**
   * <pre>
   * Import Facebook friends for the user.
   * </pre>
   *
   * <code>optional .google.protobuf.BoolValue import = 4;</code>
   */
  com.google.protobuf.BoolValue getImport();
}
