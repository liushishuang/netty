package com.shengsiyuan.netty.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:40 2019/3/19
 * <p>
 * Client            Server
 * <p>
 * Your Code         Your Code
 * Service.Client    Service.Processor
 * write()/read()    read()/write()        自动生成的
 * TProtocol         TProtocol             协议层协议
 * TTransport        TTransport            传输层
 * 网络层             网络层
 */
public class ThriftClient {
    public static void main(String[] args) {
        //socket 和服务端保持一致的传输层和协议层对象
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(transport);

        //client
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            transport.open();

            //通过网络向服务器发送请求,调用方法
            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("=====");

            Person person1 = new Person()
                    .setUsername("李四")
                    .setAge(20)
                    .setMarried(true);
            client.savePerson(person1);

        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        } finally {
            transport.close();
        }
    }
}
