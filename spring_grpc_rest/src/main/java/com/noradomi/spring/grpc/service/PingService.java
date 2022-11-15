package com.noradomi.spring.grpc.service;

import com.google.gson.Gson;
import com.noradomi.spring.grpc.protobuf.PingRequest;
import com.noradomi.spring.grpc.protobuf.PingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Created by phucvt Date: 15/11/2022 */
@Slf4j
@Service
public class PingService {
  public PingResponse ping(PingRequest request) {
    log.info("Received Ping message {}", new Gson().toJson(request));
    return PingResponse.newBuilder()
        .setTimestamp(System.currentTimeMillis())
        .setMessage("Pong")
        .build();
  }
}
