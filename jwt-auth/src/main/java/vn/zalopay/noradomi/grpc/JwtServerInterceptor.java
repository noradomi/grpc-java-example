package vn.zalopay.noradomi.grpc;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import vn.zalopay.noradomi.constant.Constant;

/** Created by phucvt Date: 15/11/2022 */
public class JwtServerInterceptor implements ServerInterceptor {
  private JwtParser parser = Jwts.parser().setSigningKey(Constant.JWT_SIGNING_KEY);

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> serverCall,
      Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {
    String value = metadata.get(Constant.AUTHORIZATION_METADATA_KEY);

    Status status = Status.OK;
    if (value == null) {
      status = Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
    } else if (!value.startsWith(Constant.BEARER_TYPE)) {
      status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
    } else {
      Jws<Claims> claims = null;
      String token = value.substring(Constant.BEARER_TYPE.length()).trim();
      try {
        claims = parser.parseClaimsJws(token);
      } catch (JwtException e) {
        status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
      }
      if (claims != null) {
        Context ctx =
            Context.current()
                .withValue(Constant.CLIENT_ID_CONTEXT_KEY, claims.getBody().getSubject());
        return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
      }
    }
    serverCall.close(status, new Metadata());
    return new ServerCall.Listener<ReqT>() {};
  }
}
