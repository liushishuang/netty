package com.shengsiyuan.netty.sixth;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:32 2019/3/17
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {

    }

    //向服务端发送对象
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MyDataInfo.Person person = MyDataInfo.Person
                .newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("北京")
                .build();
        ctx.channel().writeAndFlush(person);
    }
}
