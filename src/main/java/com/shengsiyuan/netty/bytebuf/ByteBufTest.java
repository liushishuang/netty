package com.shengsiyuan.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:35 2019/4/2
 * <p>
 * 通过索引访问Byte时不会改变真实的读索引和写索引;通过readerIndex()和writerIndex()方法分别直接修改索引
 * 1. 存储字节的数组是动态的,最大默认值为Integer.MAX_VALUE. 读写索引分开使用方便
 * ======================================================
 * ByteBuf提供三种缓冲区类型
 * - 后端编码/解码推荐使用heap buffer
 * - 对弈I/O通信线程在读写缓冲区时,推荐使用direct buffer
 * 1. Netty的ByteBuf采用了读写索引分离的策略(readerIndex和writerIndex),初始值都为0.
 * 2. 当度索引和写索引处于同一个位置时,如果我们继续读取,就会抛出IndexOutOfBoundsException
 * 3. 对于ByteBuf的任何读写操作都会分别维护读索引和写索引
 * <p>
 * heap buffer 数据存储到jvm堆空间中,并且将实际的数据放到byte array中实现;
 * 优点: 快速创建和销毁,但是每次数据读写
 * 缺点: 每次读写数据的时候,需要将数据复制到直接缓冲区中再进行网络传输.
 * <p>
 * direct buffer: 在堆之外直接分配空间,直接缓冲区并不会占用堆的空间,因为它是操作系统在本地内存进行的数据分配.并不支持通过字节数组的方式访问.
 * 优点: 数据传递性能好
 * 缺点: 直接在操作系统内存中,内存空间的分配与释放要比堆空间更加复杂, 而且速度慢一些.(Netty通过内存池来解决这个问题)
 * composite buffer 复合缓冲区
 */
public class ByteBufTest {
    public static void main(String[] args) {
        //创建对象
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf directBuffer = Unpooled.directBuffer(8);

        ByteBuf heapBuffer = Unpooled.buffer(10);
        //创建长度33(变长,最大3个字节) readerIndex 0 writerIndex 11
        ByteBuf byteBuf = Unpooled.copiedBuffer("张hello world", StandardCharsets.UTF_8);

        //判断是否为堆上缓冲(字节数组)
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            System.out.println(new String(content, StandardCharsets.UTF_8));
            System.out.println(byteBuf);

            //0 数组偏移量(一般都是0)
            int arrayOffset = byteBuf.arrayOffset();
            //0 读索引
            int readerIndex = byteBuf.readerIndex();
            //11 写索引
            int writerIndex = byteBuf.writerIndex();
            //33 容量(自动扩容)
            int capacity = byteBuf.capacity();
            //可读字节数=writerIndex-readerIndex
            int readableBytes = byteBuf.readableBytes();
            byteBuf.getCharSequence(0, byteBuf.readableBytes(), StandardCharsets.UTF_8);
            //writerIndex > readerIndex
            boolean readable = byteBuf.isReadable();
        }

    }
}
