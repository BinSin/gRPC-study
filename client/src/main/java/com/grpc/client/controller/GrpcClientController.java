package com.grpc.client.controller;

import com.grpc.client.service.GrpcClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GrpcClientController {

  private final GrpcClientService grpcClientService;

  @GetMapping("/test")
  public String printMessage(@RequestParam(name = "name") String name) {
    return grpcClientService.receiveGreeting(name);
  }

}
