package com.shengsiyuan.netty.sixth;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import static com.shengsiyuan.netty.sixth.MyDataInfo.MyMessage;
import java.util.Random;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:32 2019/3/17
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {

    }

    //向服务端发送对象
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //012
        int randomInt = new Random().nextInt(3);


        MyMessage myMessage = null;
        if (0 == randomInt) {
            myMessage = MyMessage
                .newBuilder()
                    .setDataType(MyMessage.DataType.PersonType)
                    .setPerson(
                            MyDataInfo.Person.newBuilder()
                .setName("张三")
                .setAge(20)
                                    .setAddress("北京").build()
                    )
                    .build();

        } else if (1 == randomInt) {
            myMessage = MyMessage
                    .newBuilder()
                    .setDataType(MyMessage.DataType.DogType)
                    .setDog(
                            MyDataInfo.Dog.newBuilder()
                                    .setName("一只狗")
                                    .setAge(3)
                                    .build()
                    )
                .build();
        } else {
            myMessage = MyMessage
                    .newBuilder()
                    .setDataType(MyMessage.DataType.CatType)
                    .setCat(
                            MyDataInfo.Cat.newBuilder()
                                    .setName("一只猫")
                                    .setCity("北京")
                                    .build()
                    )
                    .build();

        }


//        ctx.channel().writeAndFlush(person);
    }
}
