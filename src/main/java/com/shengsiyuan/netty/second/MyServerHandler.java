package com.shengsiyuan.netty.second;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 21:21 2019/3/16
 *
 * 1. Netty处理器可以分为两类: 入站处理器和出站处理器.
 * 2. 入站处理器的顶层是ChannelInboundHandler,出站处理器的顶层是ChannelOutboundHandler.
 * 3. 数据处理时常用的各种编解码器的本质都是处理器.
 * 4. 编解码器: 无论我们向网络上写入的数据是什么类型(int,char,String,二进制),数据在网络中传递时,其都是以字节流的形式呈现的;
 * ^            编码(encode):数据由原本的形式转换为字节流的操作. 解码(decode): 字节转换为原本的格式(或者其它格式)的操作. 统称为codec
 * 5. 编码: 本质上是一种出栈处理器(程序到网络去传输)
 * 6. 解码: 本质上是一种入站处理器(网络流到程序参与计算)
 * 7. 在Netty中,编码器通常以XXXEncoder命名;解码器通常以XXXDecoder命名  (可以自定义操作)
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {


    /**
     * 不要将长时间执行的耗时任务放入到EventLoop中,而是使用一个专门的EventExecutor
     * 接收客户端数据,并返回数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ":" + msg);
        ctx.channel().writeAndFlush("from server: " + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
