package com.shengsiyuan.netty.fifth;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 16:38 2019/3/17
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        //用块的方式写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //http消息聚合
        pipeline.addLast(new HttpObjectAggregator(8192));
        //协议  ws;//server:port/context_path
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
