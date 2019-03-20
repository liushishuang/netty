package com.shengsiyuan.netty.grpc;

import com.shengsiyuan.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

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

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);

        //传递请求参数,获取返回值
        MyResponse myResponse = blockingStub.getRealNameByUsername(
                MyRequest.newBuilder()
                        .setUsername("zhangsan")
                        .build());

        System.out.println(myResponse.getRealname());

        System.out.println("===================");
        Iterator<StudentResponse> iterator = blockingStub.getStudentsByAge(
                StudentRequest.newBuilder()
                        .setAge(20)
                        .build());
        while (iterator.hasNext()) {
            StudentResponse studentResponse = iterator.next();
            System.out.println(studentResponse.getName() + "," + studentResponse.getAge() + "," + studentResponse.getCity());
        }

        System.out.println("==================");

        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                //服务器返回结果是一个集合
                value.getStudentResponseList().forEach(studentResponse -> {
                    System.out.println(studentResponse.getName());
                    System.out.println(studentResponse.getAge());
                    System.out.println(studentResponse.getCity());
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




    }
}
