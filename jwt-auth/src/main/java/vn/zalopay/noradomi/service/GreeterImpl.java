package vn.zalopay.noradomi.service;

import io.grpc.stub.StreamObserver;
import java.util.logging.Logger;
import vn.zalopay.noradomi.constant.Constant;
import vn.zalopay.noradomi.helloworld.GreeterGrpc;
import vn.zalopay.noradomi.helloworld.HelloReply;
import vn.zalopay.noradomi.helloworld.HelloRequest;

/** Created by phucvt Date: 15/11/2022 */
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {
  private static final Logger LOGGER = Logger.getLogger(GreeterImpl.class.getName());

  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    String clientId = Constant.CLIENT_ID_CONTEXT_KEY.get();
    LOGGER.info("Processing request from " + clientId);
    HelloReply reply = HelloReply.newBuilder().setMessage("Hello, " + request.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
