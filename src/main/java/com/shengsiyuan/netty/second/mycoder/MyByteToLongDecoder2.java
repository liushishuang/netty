package com.shengsiyuan.netty.second.mycoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 20:20 2019/4/6
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode2 invoked");
        out.add(in.readLong());
    }
}
