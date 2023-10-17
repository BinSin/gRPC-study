package com.grpc.server.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.calc.lib.CalcReply;
import net.devh.boot.grpc.calc.lib.CalcRequest;
import net.devh.boot.grpc.calc.lib.CalcServiceGrpc.CalcServiceImplBase;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class CalcServerService extends CalcServiceImplBase {

  private static final Logger logger = LoggerFactory.getLogger(CalcServerService.class);

  @Override
  public void calc(CalcRequest request, StreamObserver<CalcReply> responseObserver) {
    logger.info("receive num: {}, {}", request.getNum1(), request.getNum2());

    int num1 = request.getNum1();
    int num2 = request.getNum2();

    CalcReply reply = CalcReply.newBuilder()
        .setSum(num1 + num2)
        .setMinus(num1 - num2)
        .setMultiply(num1 * num2)
        .setDivide((double) num1 / num2)
        .build();

    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

}
