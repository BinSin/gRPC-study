# gRPC with Spring Boot

## 환경

---

- Multimodule
  - proto
    - proto 파일에 대한 stub class를 생성해 놓는 모듈
  - client
    - gRPC 통신을 하는 모듈
  - server
    - gRPC 통신을 받는 모듈

## Proto

---

### 주요 세팅

- MessageService.proto
  - Message를 날리는 서비스에 대한 proto 파일

    ```protobuf
    syntax = "proto3";
    
    option java_multiple_files = true;
    option java_package = "net.devh.boot.grpc.message.lib"; # 이 package로 class 생성
    option java_outer_classname = "MessageProto"; # 이 클래스명으로 생성
    
    // The greeting service definition.
    service MessageService {
      // Sends a greeting
      rpc SayHello (MessageRequest) returns (MessageReply); # MessageRequest를 받고 MessageReply를 반환하는 SayHello 메서드 생성
      rpc SayBye (MessageRequest) returns (MessageReply); # MessageRequest를 받고 MessageReply를 반환하는 SayBye 메서드 생성
    }
    
    // The request message containing the user's name.
    message MessageRequest {
      string name = 1; # 첫번째 파라미터로 세팅
    }
    
    // The response message containing the greetings
    message MessageReply {
      string message = 1; # 두번째 파라미터로 세팅
    }
    ```

- CalcService.proto
  - 위와 동일하게 계산식을 구하는 서비스에 대한 proto 파일
- **g**r**adle build 를 통해 stub class 생성**

## Server

---

### 주요 세팅

- application.yml

    ```protobuf
    grpc:
      server:
        port: 9090 # grpc server 포트 설정
    ```

- GrpcServerService

    ```java
    package com.grpc.server.service;
    
    import io.grpc.stub.StreamObserver;
    import net.devh.boot.grpc.message.lib.MessageReply;
    import net.devh.boot.grpc.message.lib.MessageRequest;
    import net.devh.boot.grpc.message.lib.MessageServiceGrpc.MessageServiceImplBase;
    import net.devh.boot.grpc.server.service.GrpcService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    
    @GrpcService
    public class GrpcServerService extends MessageServiceImplBase { // {서비스명}ImplBase 으로 인터페이스가 생성되므로 이를 상속받아서 각 메서드를 재정의
    
      // lombok이 되지 않음
      private static final Logger logger = LoggerFactory.getLogger(GrpcServerService.class);
    
      @Override // proto 파일에서 정의한 메서드 재정의
      public void sayHello(MessageRequest request, StreamObserver<MessageReply> responseObserver) {
        logger.info("receive name: {}", request.getName());
    
        // 1. 전달받은 request를 처리
        MessageReply reply = MessageReply.newBuilder()
            .setMessage("Hello ==> " + request.getName()) // 2. MessageReply에서 정의한 message 세팅
            .build();
    
        responseObserver.onNext(reply); // 2. client에 response 전달
        responseObserver.onCompleted(); // 3. 통신 종료
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
    ```

- CalcServerService
  - 위와 동일하게 세팅

## Client

---

- application.yml

    ```protobuf
    grpc:
      client:
        server1: # 아래 주소에 대한 grpc server 명을 지정
          address: 'static://127.0.0.1:9090' # server 주소 세팅
          negotiation-type: plaintext
    ```

- GrpcClientService

    ```java
    @Service
    public class GrpcClientService {
    
      @GrpcClient("server1") // yml에 설정한 명칭와 일치해야 한다!!
      private MessageServiceBlockingStub messageServiceStub; // {서비스명}BlockingStub 으로 stub 클래스가 생성됨
    
      @GrpcClient("server1")
      private CalcServiceBlockingStub calcServiceStub;
    
      public String sayHello(String name) {
    		// proto 파일에 정의한 MessageRequest를 정의
        MessageRequest request = MessageRequest.newBuilder()
            .setName(name)
            .build();
    
    		// sayHello 사용 시, server 통신을 통해 message 가져옴
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
    ```