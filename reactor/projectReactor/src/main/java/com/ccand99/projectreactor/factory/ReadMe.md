## 工厂方法创建流：

### push工厂方法

通过单线程生产者(在同一时间只有一个线程,可以调用next，complete或error)创建Flux实例，此方法适配于异步，单线程，多值Api，无须关注背压和取消。

同样可以桥接接口，示例见create示例（把create换成push）

push()并且create()两者都允许设置onRequest使用者以管理请求量并确保仅在有待处理的请求时才通过接收器推送数据。

onCancel 首先调用，仅用于取消信号。

onDispose 为完成，错误或取消信号而调用。

### create工厂方法

从不同线程发送事件，会序列化FluxSink。和push都支持重载溢出策略，能通过注册额外的处理程序清理资源。可以支持接口桥接（官网有示例）

此外，由于create可以桥接异步API并管理背压，因此您可以通过指示以下内容来完善如何进行背压行为

OverflowStrategy：

IGNORE完全忽略下游背压请求。当队列下游充满时，可能会IllegalStateException。

ERRORIllegalStateException在下游无法跟上时发出信号。

DROP 如果下游没有准备好接收它，则丢弃输入的信号。

LATEST 让下游只从上游获取最新信号。

BUFFER（默认值），以在下游无法跟上时缓冲所有信号。（这会实现无限缓冲，并可能导致OutOfMemoryError）。

### generate工厂方法

适用在基于生成器内部处理状态创建复杂序列。接收器为一个 SynchronousSink，其next()方法每次回调调用最多只能调用一次

### Using工厂方法

using工厂方法能根据一个disposable创建流，在响应式中实现了try-with-resources。
```java
Flux<String> flux =
Flux.using(
        () -> disposableInstance, 
        disposable -> Flux.just(disposable.toString()), 
        Disposable::dispose 
);
```

### usingWhen工厂

包装响应式事务。using通过Callable实例获取资源，usingWhen通过订阅Publisher。
