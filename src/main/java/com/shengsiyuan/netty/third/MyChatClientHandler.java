package com.shengsiyuan.netty.third;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 23:40 2019/3/16
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }
}
