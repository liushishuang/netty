package com.shengsiyuan.netty.first;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 18:16 2019/3/16
 * 传递SocketChannel
 * initChannel中获取pipeline对象
 * HttpServerCodec: 处理http请求
 * LengthFieldBasedFrameDecoder,LengthFieldPrepender: 处理粘包问题
 * StringDecoder,StringEncoder: 字符串编码问题
 * DelimiterBasedFrameDecoder: 聊天消息使用
 * IdleStateHandler: 空闲状态监测处理器
 * ChunkedWriteHandler,HttpObjectAggregator,WebSocketServerProtocolHandler: Websocket使用
 * ProtobufEncoder,ProtobufDecoder,ProtobufVarint32LengthFieldPrepender,ProtobufVarint32FrameDecoder: 传递Protobuf支持
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
