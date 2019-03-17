package com.shengsiyuan.netty.first;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 18:16 2019/3/16
 * channel注册好后,自动创建并执行代码
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //管道
        ChannelPipeline pipeline = ch.pipeline();
        //每次创建新的对象(多实例)
        //http编码和解码封装
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());
    }
}
