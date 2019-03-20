package com.shengsiyuan.netty.sixth;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:28 2019/3/17
 * 使用protobuf实现消息的传递(protobuf只能作为消息;而thrift既可以作为消息,也能实现server/client,取代netty)
 * <p>
 * IDL(Interface Description Language)
 * 不支持无符号类型和日期类型
 * 集合中的元素可以是除了service外的任何类型,包括exception
 * 工作原理: 数据传输使用socket(多种语言均支持),数据再以特定的格式发送,接收方语言进行解析
 * 数据类型: byte i16 i32 i64 double string
 * 容器类型: list,set,map
 * 组成: struct, service(类似接口),类型定义(typedef),常量定义
 * <p>
 * <p>
 * 解决不同语言使用protobuf文件
 * (一)
 * git submodule
 * <p>
 * ServerProject
 * Protobuf-Java(生成文件的库,作为仓库里的仓库被其它项目引用)
 * ClientProject
 * data.proto(源代码仓库)
 * <p>
 * branch:
 * //外层的环境
 * develop
 * test 测试和生产环境一致
 * master
 * //里层的环境
 * develop
 * test
 * master
 * 1. 开发环境配置低; 测试和生产环境需要一致,并且配置高
 * 2. 分支切换紊乱 外层+里层
 * 3. 笨重,并且里面修改后推回复杂
 * <p>
 * ==========================
 * (二)
 * 每次修改后打包,并修改版本号
 * (三)
 * git subtree
 * 拉取代码后,不会产生中间仓库问题,而是作为整体的仓库
 */
public class TestClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new TestClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
