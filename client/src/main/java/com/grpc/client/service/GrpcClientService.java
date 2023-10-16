package com.grpc.client.service;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.examples.lib.HelloRequest;
import net.devh.boot.grpc.examples.lib.MyServiceGrpc.MyServiceBlockingStub;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrpcClientService {

  @GrpcClient("my-service")
  private MyServiceBlockingStub myServiceStub;

  public String receiveGreeting(String name) {
    HelloRequest request = HelloRequest.newBuilder()
        .setName(name)
        .build();

    return myServiceStub.sayHello(request).getMessage();
  }

}
