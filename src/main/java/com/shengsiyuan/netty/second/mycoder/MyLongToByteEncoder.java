package com.shengsiyuan.netty.second.mycoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 20:21 2019/4/6
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("encode invoked");
        System.out.println(msg);
        out.writeLong(msg);
    }
}
