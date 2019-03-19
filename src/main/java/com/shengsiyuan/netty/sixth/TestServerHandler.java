//package com.shengsiyuan.netty.sixth;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//
///**
// * @Author: LiuShishuang
// * @Description:TODO
// * @Date: 22:21 2019/3/17
// */
//public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {
//
//    //接收消息后的处理
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {
//        System.out.println(msg.getName());
//        System.out.println(msg.getAge());
//        System.out.println(msg.getAddress());
//    }
//
//
//}
