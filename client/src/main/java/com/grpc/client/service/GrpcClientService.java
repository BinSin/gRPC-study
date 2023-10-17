package com.grpc.client.service;

import net.devh.boot.grpc.calc.lib.CalcRequest;
import net.devh.boot.grpc.calc.lib.CalcServiceGrpc.CalcServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.message.lib.MessageRequest;
import net.devh.boot.grpc.message.lib.MessageServiceGrpc.MessageServiceBlockingStub;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

  @GrpcClient("server1") // yml에 설정한 명칭와 일치해야 한다!!
  private MessageServiceBlockingStub messageServiceStub;

  @GrpcClient("server1")
  private CalcServiceBlockingStub calcServiceStub;

  public String sayHello(String name) {
    MessageRequest request = MessageRequest.newBuilder()
        .setName(name)
        .build();

    return messageServiceStub.sayHello(request).getMessage();
  }

  public String sayBye(String name) {
    MessageRequest request = MessageRequest.newBuilder()
        .setName(name)
        .build();

    return messageServiceStub.sayBye(request).getMessage();
  }

  public String getCalc(int num1, int num2) {
    CalcRequest request = CalcRequest.newBuilder()
        .setNum1(num1)
        .setNum2(num2)
        .buildPartial();

    return calcServiceStub.calc(request).toString();
  }

}
