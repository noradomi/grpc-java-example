package vn.zalopay.noradomi.constant;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

import io.grpc.Context;
import io.grpc.Metadata;

/** Created by phucvt Date: 15/11/2022 */
public class Constant {
  public static final String JWT_SIGNING_KEY = "L8hHXsaQOUjk5rg7XPGv4eL36anlCrkMz8CJ0i/8E/0=";
  public static final String BEARER_TYPE = "Bearer";

  public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY =
      Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
  public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("clientId");

  private Constant() {
    throw new AssertionError();
  }
}
