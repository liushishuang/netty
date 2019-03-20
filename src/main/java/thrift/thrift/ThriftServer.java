package thrift.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.PersonService;

/**
 * @Author: LiuShishuang
 * @Description:TODO
 * @Date: 22:35 2019/3/19
 */
public class ThriftServer {
    public static void main(String[] args) throws TTransportException {
        //socket连接   多线程,单线程,非阻塞
//        new TServerSocket(8899);  //阻塞式对象
        TNonblockingServerSocket socket = (new TNonblockingServerSocket(8899));


        THsHaServer.Args arg = new THsHaServer.Args(socket)
                .minWorkerThreads(2)
                .maxWorkerThreads(4);


        //传输方式(用什么传输)
//        new TBinaryProtocol.Factory();  //二进制
//        new TCompactProtocol.Factory(); //压缩(常用)
//        new TJSONProtocol.Factory();  //json
//        new TSimpleJSONProtocol.Factory(); //很少用,json只写协议,生成的文件容易通过脚本语言解析.缺少必要的元数据信息
//        new TTupleProtocol.Factory();
        arg.protocolFactory(new TCompactProtocol.Factory());

        //传输格式(协议)
//        new TFastFramedTransport.Factory(); //以frame为单位进行传输非阻塞服务使用
//        new TFileTransport(); //以文件形式传输
//        new TMemoryInputTransport(); //将内存用于I/O,Java实现时使用了简单的ByteArrayOutputStream
//        new TZlibTransport(); //使用zlib进行压缩,与其它传输方式联合使用.当前无java实现
        arg.transportFactory(new TFramedTransport.Factory());


        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
        arg.processorFactory(new TProcessorFactory(processor));


        //Server服务模型
//        new TSimpleServer(); //简单单线程服务模型,常用于测试
//        new TThreadPoolServer(); //多线程服务模型,标准的阻塞式ＩＯ
//        new TNonblockingServer();//多线程服务模型,非阻塞IO(需要使用TFramedTransPort数据传输模型)
//        new THsHaServer(); //引入了线程池处理读写任务,依赖TFramedTransPort;Half-sync/Half-async ,前者处理IO事件(accept/read/write io) 后者处理handler对RPC的同步处理
        TServer server = new THsHaServer(arg);

        System.out.println("Thrift Server Started");

        //死循环,服务器永远运行
        server.serve();
    }
}
