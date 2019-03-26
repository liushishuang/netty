package com.shengsiyuan.netty.third;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:54 2019/3/16
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 连接  : 打印已经上线,加入到group中并且通知其它
     * 未连接: 打印已经下线,通知其它
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + "发送的消息: " + msg + "\n");
            } else {
                ch.writeAndFlush("[自己]" + msg + "\n");
            }
        });
    }

    //建立好连接后,向所有其它的对象广播
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //向channelGroup中的每个对象发送消息
        channelGroup.writeAndFlush("[服务器] -  " + channel.remoteAddress() + "加入 \n");

        //添加到group中
        channelGroup.add(channel);
    }

    //连接断掉
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器] -  " + channel.remoteAddress() + "离开 \n");
        System.out.println(channelGroup.size());
        //当连接断掉后,自动去移除操作
//        channelGroup.remove(channel);
    }

    //连接处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
