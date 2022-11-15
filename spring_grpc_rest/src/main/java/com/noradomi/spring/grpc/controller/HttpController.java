package com.noradomi.spring.grpc.controller;

import com.noradomi.spring.grpc.protobuf.PingRequest;
import com.noradomi.spring.grpc.service.PingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Created by phucvt Date: 15/11/2022 */
@RestController
@RequestMapping("/api")
@Slf4j
public class HttpController {
  @Autowired private PingService pingService;

  @PostMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ping(@RequestBody PingRequest request) {
    return ResponseEntity.ok(pingService.ping(request));
  }
}
