package com.shengsiyuan.netty.grpc;

import com.shengsiyuan.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 23:00 2019/3/20
 */
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 8899)
                .usePlaintext(true)
                .build();
        //同步阻塞
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);

        //一
        //传递请求参数,获取返回值
        MyResponse myResponse = blockingStub.getRealNameByUsername(
                MyRequest.newBuilder()
                        .setUsername("zhangsan")
                        .build());

        System.out.println(myResponse.getRealname());

        System.out.println("===================");
        //二
        Iterator<StudentResponse> iterator = blockingStub.getStudentsByAge(
                StudentRequest.newBuilder()
                        .setAge(20)
                        .build());
        while (iterator.hasNext()) {
            StudentResponse studentResponse = iterator.next();
            System.out.println(studentResponse.getName() + "," + studentResponse.getAge() + "," + studentResponse.getCity());
        }

        System.out.println("==================");
        //三
        //只要客户端以流式方式向服务器发送请求, 必须使用异步的方式(调用后complete,还没有结果...)
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);

        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                //服务器返回结果是一个集合
                value.getStudentResponseList().forEach(studentResponse -> {
                    System.out.println(studentResponse.getName());
                    System.out.println(studentResponse.getAge());
                    System.out.println(studentResponse.getCity());
                    System.out.println("********");
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };
        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentWrapperByAges(studentResponseListStreamObserver);

        studentRequestStreamObserver.onNext(StudentRequest.newBuilder()
                .setAge(20)
                .build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder()
                .setAge(30)
                .build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder()
                .setAge(40)
                .build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder()
                .setAge(50)
                .build());
        //客户端调用结束
        studentRequestStreamObserver.onCompleted();

        //四: 双向的流式的数据传递
        StreamObserver<StreamRequest> requestStreamObserver = stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                //收到服务器
                System.out.println(value.getResponseInfo());

            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });
        for (int i = 0; i < 10; i++) {
            requestStreamObserver.onNext(
                    StreamRequest.newBuilder()
                            .setRequestInfo(LocalDateTime.now().toString())
                            .build()
            );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
