package com.noradomi.spring.grpc.controller;

import com.noradomi.spring.grpc.protobuf.PingRequest;
import com.noradomi.spring.grpc.protobuf.PingResponse;
import com.noradomi.spring.grpc.protobuf.PingServiceGrpc;
import com.noradomi.spring.grpc.service.PingService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/** Created by phucvt Date: 15/11/2022 */
@Slf4j
@GRpcService
public class GrpcController extends PingServiceGrpc.PingServiceImplBase {
  @Autowired private PingService pingService;

  @Override
  public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
    responseObserver.onNext(pingService.ping(request));
    responseObserver.onCompleted();
  }
}
