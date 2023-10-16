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

  @GetMapping("/hello")
  public String sayHello(@RequestParam(name = "name") String name) {
    return grpcClientService.sayHello(name);
  }

  @GetMapping("/bye")
  public String sayBye(@RequestParam(name = "name") String name) {
    return grpcClientService.sayBye(name);
  }

  @GetMapping("/sum")
  public int getSum(@RequestParam(name = "num1") int num1, @RequestParam(name = "num2") int num2) {
    return grpcClientService.getSum(num1, num2);
  }

}
