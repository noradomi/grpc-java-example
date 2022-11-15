package vn.zalopay.noradomi.authorization;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.concurrent.Executor;
import vn.zalopay.noradomi.constant.Constant;

/** Created by phucvt Date: 15/11/2022 */
public class JwtCredential extends CallCredentials {

  private final String subject;

  public JwtCredential(String subject) {
    this.subject = subject;
  }

  @Override
  public void applyRequestMetadata(
      final RequestInfo requestInfo,
      final Executor executor,
      final MetadataApplier metadataApplier) {
    // Make a JWT compact serialized string.
    // This example omits setting the expiration, but a real application should do it.
    final String jwt =
        Jwts.builder()
            .setSubject(subject)
            .signWith(SignatureAlgorithm.HS256, Constant.JWT_SIGNING_KEY)
            .compact();

    executor.execute(
        () -> {
          try {
            Metadata headers = new Metadata();
            headers.put(
                Constant.AUTHORIZATION_METADATA_KEY,
                String.format("%s %s", Constant.BEARER_TYPE, jwt));
            metadataApplier.apply(headers);
          } catch (Exception e) {
            metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
          }
        });
  }

  public String generateToken() {
    return Jwts.builder()
        .setSubject(subject)
        .signWith(SignatureAlgorithm.HS256, Constant.JWT_SIGNING_KEY)
        .compact();
  }

  @Override
  public void thisUsesUnstableApi() {
    // noop
  }
}
