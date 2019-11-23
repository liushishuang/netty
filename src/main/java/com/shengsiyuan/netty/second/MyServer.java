package com.shengsiyuan.netty.second;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 21:10 2019/3/16
 * <p>
 * 传统模式：一个socket对应服务端一个线程
 * 1. 服务器端线程有限
 * 2. 线程间上下文切换成本高
 * 3. 存活线程的浪费
 * ＝＝＝＝＝
 * Reactor模式: 一个线程解决所有的连接.
 * 1. Handle(句柄或描述符): 本质上是表示一种资源,是由操作系统提供的;该资源用来表示一个个的事件,比如说文件描述符,针对网络编程的Socket描述符.
 * 事件既可以来自外部(客户端的连接,客户端发送的数据),也可以来自内部(操作系统定时器事件等,本质上就是文件描述符);Handle本身是事件产生的发源地.
 * 2. Synchronous Event Demultiplexer(同步事件分离器): 本身是系统调用,用于等待事件的发生(事件可能是一个,也可能是多个),调用方在调用的时候会阻塞,
 * 一直阻塞到同步事件分离器上有事件产生为止. 对于Linux来说,指的就是常用的I/O多路复用机制,比如说select,poll,epoll等; 在Java NIO领域中,对应的组件
 * 就是Selector,对应的阻塞的方法就是select方法
 * 3. Event Handler(事件处理器): 本身由多个回调方法构成,这些回调方法构成了与应用相关的对于某个事件的反馈. Netty相比于Java NIO来说,在事件处理器上
 * 进行了升级,为我们提供了大量的回调方法(SimpleChannelInboundHandler等),供我们进行业务逻辑的 处理.
 * 4. Concrete Event Handler(具体事件处理器): 对回调方法进行了实现,从而实现了特定业务逻辑.本质上就是我们编写的Handler
 * 5. Initiation Dispatcher(初始分发器): 实际上就是Reactor角色,定义了一些规范,用于控制事件的调度方式,同时又提供了事件处理器的注册,删除等设施.
 * 本身是事件处理器的核心所在,会通过同步事件分离器等待事件发生,一旦事件发生,就是分离出每一个事件,然后使用事件处理器,调用相关的回调方法来处理事件.
 * =>
 * BossGroup: Selector监听客户端注册事件,accept后得到SelectionKey,如果是readable,得到socketChannel
 * workerGroup: Selector注册socketChannel
 * ======================================
 * Reactor模式的流程:
 * 1.当应用向Initiation Dispatcher注册具体的Event Handler时,应用会标识出希望 Initiation Dispatcher在某个Handle发生时向其通知的该事件,该事件与Handle关联
 * 2. Initiation Dispatcher会要求每个Event Handler向其传递内部的Handle,该Handle向操作系统标识了Event Handler.
 * 3. 当所有的Event Handler注册完毕后,应用会调用handle_event方法来启动Initiation Dispatcher的事件循环. 这时,Initiation Dispatcher会将每个这个的Event Handler
 * 的Handle合并起来,并使用Synchronous Event等待这些事件的发生,比如说,TCP协议会使用select同步事件分离器等待客户端发送的数据到达连接的socket handler上.
 * 4.当与某个事件源对应的Handle变为ready状态时(比如说,TCP socket变为ready read时), Synchronous Event就会通知Initiation Dispatcher.
 * 5. Initiation Dispatcher会触发Event Handler的回调方法,从而响应这个处于ready状态的handle,当Handle发生时,Initiation Dispatcher会被该事件源激活的Handle
 * 作为Key来寻找分发恰当的Event Handler回调方法.
 * 6. Initiation Dispatcher会回调Event Handler的handle_event回调方法来执行特定于应用的功能(开发者自己所编写的功能),从而响应这个事件,所发生的的事件类型可以
 * 作为该方法参数并被该方法内部使用来执行额外的特定于服务的分离与分发.
 */
public class MyServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            ChannelFuture channelFuture = serverBootstrap
                    .bind(8899)
                    .sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
