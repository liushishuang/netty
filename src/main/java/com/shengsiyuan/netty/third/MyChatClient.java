package com.shengsiyuan.netty.third;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 23:36 2019/3/16
 */
public class MyChatClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    //客户端使用handler,针对boss
                    .handler(new MyChatClientInitializer());
            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();
//            channelFuture.channel().closeFuture().sync();
            //不断读取内容
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(System.in)));
            for (; ; ) {
                channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
