package com.shengsiyuan.netty.first;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 18:16 2019/3/16
 * 自定义通道初始化器
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //管道,并添加编解码器(每次进行new,多实例)
        ChannelPipeline pipeline = ch.pipeline();
        //每次创建新的对象(多实例)
        //httpServer处理
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());
    }
}
