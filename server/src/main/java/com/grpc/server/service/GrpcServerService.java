package com.grpc.server.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.message.lib.MessageReply;
import net.devh.boot.grpc.message.lib.MessageRequest;
import net.devh.boot.grpc.message.lib.MessageServiceGrpc.MessageServiceImplBase;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class GrpcServerService extends MessageServiceImplBase {

  private static final Logger logger = LoggerFactory.getLogger(GrpcServerService.class);

  @Override
  public void sayHello(MessageRequest request, StreamObserver<MessageReply> responseObserver) {
    logger.info("receive name: {}", request.getName());

    MessageReply reply = MessageReply.newBuilder()
        .setMessage("Hello ==> " + request.getName())
        .build();

    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

  @Override
  public void sayBye(MessageRequest request, StreamObserver<MessageReply> responseObserver) {
    logger.info("receive name: {}", request.getName());

    MessageReply reply = MessageReply.newBuilder()
        .setMessage("Bye ==> " + request.getName())
        .build();

    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

}
