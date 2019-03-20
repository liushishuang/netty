package com.shengsiyuan.netty.grpc;

import com.shengsiyuan.proto.*;
import io.grpc.stub.StreamObserver;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:45 2019/3/20
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端信息: " + request.getUsername());
        //设置返回值
        responseObserver.onNext(
                MyResponse.newBuilder()
                        .setRealname("张三")
                        .build());
        //标识方法调用结束
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到客户端信息: " + request.getAge());

        responseObserver.onNext(
                StudentResponse.newBuilder()
                        .setName("张三")
                        .setAge(20)
                        .setCity("北京")
                        .build());
        responseObserver.onNext(
                StudentResponse.newBuilder()
                        .setName("李四")
                        .setAge(30)
                        .setCity("上海")
                        .build());
        responseObserver.onNext(
                StudentResponse.newBuilder()
                        .setName("王五")
                        .setAge(40)
                        .setCity("广州")
                        .build());
        responseObserver.onNext(
                StudentResponse.newBuilder()
                        .setName("赵六")
                        .setAge(50)
                        .setCity("深圳")
                        .build());

        responseObserver.onCompleted();
    }

    /**
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {

        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                //接收到客户端一个请求
                System.out.println("onNext: " + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                //客户端传递完数据,需要返回response
                StudentResponse studentResponse = StudentResponse.newBuilder()
                        .setName("张三")
                        .setAge(20)
                        .setCity("北京")
                        .build();
                StudentResponse studentResponse1 = StudentResponse.newBuilder()
                        .setName("李四")
                        .setAge(30)
                        .setCity("上海")
                        .build();

                StudentResponseList studentResponseList = StudentResponseList.newBuilder()
                        .addStudentResponse(studentResponse)
                        .addStudentResponse(studentResponse1)
                        .build();
                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();
            }
        };
    }

}
