# in spring boot

1. 为了支持响应式规范，spring code 引入ReactiveAdapter和ReactiveAdapterRegistry。

ReactiveAdapter为响应式提供2种基本方法：

```java
//将任何类型转换为 Publisher<T>
<T> Publisher<T> toPublisher(@Nullable Object sourece){ ... }
//将 Publisher<T> 转为Object
Object fromPublisher(Publisher<?> publisher){ ... }
```
ReactiveAdapterRegistry使我们能将ReactiveAdapter的实例保存在一个位置并提供通用的访问。

2. 响应式I/O 

引入DataBuffer（不是java.nio库）抽象，可以实现重用缓冲区。另外抽象提供特定实现通用的解决方式。 pooledDataBuffer附加接口还有计数和内存管理功能。

spring5 提供了一个DataBufferUtils类 ：

```java

Flux<DataBuffer> reactiveHamlet = DataBufferUtils.read(
        new DefaultResourceLoader().getResource("abc.txt"),
        new DefaultDataBufferFactory(),
        1024
        );
```
响应式编码器(可以理解为响应式的序列化方式)： 提供一种将DataBuffer实例流与对象流进行互相转换的方式。

3. 响应式web： webFlux和升级版web MVC

web MVC 升级了 ResponseBodyEmitterReturnValueHandler类，用Emitter处理Publisher（有无限流的问题）。引入ReactiveTypeHandler类处理Flux和Mono

