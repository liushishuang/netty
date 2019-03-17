package com.shengsiyuan.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 21:53 2019/3/17
 */
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("北京")
                .build();
        //Java对象 => 字节数组
        byte[] student2ByteArray = student.toByteArray();
        //字节数组 => Java对象
        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);
        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }
}
