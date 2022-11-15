package vn.zalopay.noradomi.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.logging.Logger;
import vn.zalopay.noradomi.service.GreeterImpl;

/** Created by phucvt Date: 15/11/2022 */
public class AuthServer {
  private static final Logger LOGGER = Logger.getLogger(AuthServer.class.getName());
  private Server server;
  private int port;

  public AuthServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    // The port on which the server should run
    int port = 50051; // default

    final AuthServer server = new AuthServer(port);
    server.start();
    server.blockUntilShutdown();
  }

  private void start() throws IOException {
    server =
        ServerBuilder.forPort(port)
            .addService(new GreeterImpl())
            .intercept(new JwtServerInterceptor())
            .build()
            .start();
    LOGGER.info("Server started, listening on " + port);
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              @Override
              public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                AuthServer.this.stop();
                System.err.println("*** server shut down");
              }
            });
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }
}
