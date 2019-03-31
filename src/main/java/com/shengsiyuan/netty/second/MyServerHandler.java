package com.shengsiyuan.netty.second;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 21:21 2019/3/16
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {


    /**
     * 如果这里的代码如果同步,耗时,会阻塞netty => 使用业务线程池
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ":" + msg);
        ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
