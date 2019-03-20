namespace java thrift.generated
//python命名空间
namespace py py.thrift.generated

//IDL(interface description language)
//生成文件命令,生成后手动改变文件位置
// thrift --gen java src/thrift/data.thrift
// thrift --gen py src/thrift/data.thrift

//protobuf: 传输message
//thrift: 传输message + 服务端和客户端
//grpc: 基于http2,基于protobuf的消息序列化   message + 服务端和客户端

//定义数据类型，这里改变名称便于书写
typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String
typedef byte byte
typedef double double
//未经编码的字节流
typedef binary binary

//自定义message
struct Person {
 1: optional String username,
 2: optional int age,
 3: optional boolean married
// ,4: list<int> listValue
// ,5: map<int,String> mapValue
// ,6: set<int> setValue
}

//自定义异常
exception DataException{
 1: optional String message = "异常",
 2: optional String callStack,
 3: optional String date
}

//自定义方法
service PersonService{
  Person getPersonByUsername(1:required String username) throws (1: DataException dataException),

  void savePerson(1: required Person person) throws (1: DataException dataException)
}