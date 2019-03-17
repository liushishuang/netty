package com.shengsiyuan.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 18:04 2019/3/16
 */
public class TestServer {

    public static void main(String[] args) {
        //基于NIO的事件循环组(死循环不断接收发起的连接并处理)
        //使用两个线程组分别完成接收和处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //服务端启动
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //定义启动的组 + 通道 + 子处理器
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());
            //绑定端口号
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
