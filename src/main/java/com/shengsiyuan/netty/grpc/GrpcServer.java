package com.shengsiyuan.netty.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:52 2019/3/20
 */
public class GrpcServer {

    private Server server;

    private void start() throws Exception {
        this.server = ServerBuilder
                .forPort(8899)
                .addService(new StudentServiceImpl())
                .build()
                .start();

        System.out.println("server start");

        //关闭的时候调用钩子函数
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("关闭jvm");
            GrpcServer.this.stop();
        }));

        System.out.println("执行到这里");
    }

    private void stop() {
        if (null != this.server) {
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if (null != this.server) {
            this.server.awaitTermination(300000, TimeUnit.MILLISECONDS);
        }
    }

    public static void main(String[] args) throws Exception {
        GrpcServer server = new GrpcServer();
        server.start();
        server.awaitTermination();
    }
}
