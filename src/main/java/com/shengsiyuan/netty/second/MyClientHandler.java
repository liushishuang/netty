package com.shengsiyuan.netty.second;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:01 2019/3/16
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 接收服务端数据,并返回数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //远端地址
        System.out.println(ctx.channel().remoteAddress() + ":" + msg);
        ctx.writeAndFlush("from client: " + LocalDateTime.now());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自于客户端的问候!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
