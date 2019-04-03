package com.shengsiyuan.netty.second;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 21:13 2019/3/16
 * channel提供的pipeline,可以处理与当前channel关联的所有的事件和请求
 * pipeline在AbstractChannel中就初始化了(channel和pipeline互为引用)
 * Netty中所有的IO操作都是异步的
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //半包: 数据接收不完整
        //粘包问题: 不同消息在一起
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        //字符串解码解码
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

        //可以传递EventExecutor来提供线程组
        pipeline.addLast(new MyServerHandler());

    }
}
