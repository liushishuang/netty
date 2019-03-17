package com.shengsiyuan.netty.fourth;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 15:37 2019/3/17
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //空闲状态监测处理器
        pipeline.addLast(new IdleStateHandler(5,7,3, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
