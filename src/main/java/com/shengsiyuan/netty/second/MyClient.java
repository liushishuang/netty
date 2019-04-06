package com.shengsiyuan.netty.second;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 21:37 2019/3/16
 *
 */
public class MyClient {
    /**
     * 可以同时运行多个客户端
     * @param args
     */
    public static void main(String[] args) {
        //建立连接并发送,只需要一个循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    //使用NioSocketChannel而不是ServerSocketChannel
                    .channel(NioSocketChannel.class)
                    //客户端使用handler,针对boss
                    .handler(new MyClientInitializer());

            //使用connect而不是bind
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
