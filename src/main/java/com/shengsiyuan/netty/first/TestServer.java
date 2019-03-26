package com.shengsiyuan.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 18:04 2019/3/16
 */
public class TestServer {

    public static void main(String[] args) {
        //使用两个线程组分别完成接收和处理(基于NIO的事件循环组,网络编程永远是死循环)
        //接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //对连接进行处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //服务器启动器.用于简化netty服务器的创建工作
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //定义启动的组 + 通道 + 子处理器
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .handler(new LoggingHandler(LogLevel.INFO))  //日志记录
                    //java nio用于网络编程
                    .channel(NioServerSocketChannel.class)
                    //对bossGroup的处理
//                    .handler()
                    //对workerGroup的处理
                    .childHandler(new TestServerInitializer());

            //绑定端口号,等待客户端在8899端口进行连接
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            //关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅关闭(连接正常处理,不再接收新的请求)
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
