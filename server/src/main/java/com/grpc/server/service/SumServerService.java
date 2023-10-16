package com.grpc.server.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import net.devh.boot.grpc.sum.lib.SumReply;
import net.devh.boot.grpc.sum.lib.SumRequest;
import net.devh.boot.grpc.sum.lib.SumServiceGrpc.SumServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class SumServerService extends SumServiceImplBase {

  private static final Logger logger = LoggerFactory.getLogger(SumServerService.class);

  @Override
  public void sum(SumRequest request, StreamObserver<SumReply> responseObserver) {
    logger.info("receive num: {}, {}", request.getNum1(), request.getNum2());

    SumReply reply = SumReply.newBuilder()
        .setSum(request.getNum1() + request.getNum2())
        .build();

    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

}
