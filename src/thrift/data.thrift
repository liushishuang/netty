namespace java thrift.generated

//IDL(interface description language)
//生成文件命令,生成后手动改变文件位置 thrift --gen java src/thrift/data.thrift

//定义数据类型，这里改变名称便于书写
typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

//自定义message
struct Person {
 1: optional String username,
 2: optional int age,
 3: optional boolean married;
}

//自定义异常
exception  DataException{
 1: optional String message,
 2: optional String callStack,
 3: optional String date
}

//自定义方法
service PersonService{
  Person getPersonByUsername(1:required String username) throws (1: DataException dataException),

  void savePerson(1: required Person person) throws (1: DataException dataException)
}