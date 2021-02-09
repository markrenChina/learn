命令模式（事务）

结构:

Command 抽象命令类

ConcreteCommand 具体命令类

Invoker 调用者/请求者

Receiver 接收者，未抽象前，实际执行操作内容的对象

Client 客户端