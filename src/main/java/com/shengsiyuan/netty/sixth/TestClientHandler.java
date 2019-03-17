package com.shengsiyuan.netty.sixth;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:32 2019/3/17
 */
public class TestClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DataInfo.Student student = DataInfo.Student
                .newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("北京")
                .build();
        ctx.channel().writeAndFlush(student);
    }
}
