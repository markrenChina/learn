# 冷流与热流

冷流： 每次订阅时，会生成的流。官网举例http请求，每次都是一个新的请求。

热流： 每次订阅，只是订阅了流，而不是生成了流。以之前rxjava学习案例订阅温度为例，温度的流是自身维护的，订阅者只是订阅它，它发送最新数据。

### defer工厂方法提供了将热流模拟冷流的效果。

just是一个热流运算符，它在汇编时直接捕获值，然后将其重放给以后订阅该值的任何人。
```java
//just操作符会立即执行，但是defer操作符提供了只有在订阅时执行的效果。
 Mono.defer( ()-> Mono.just("A"));
```

### 冷流转热流 

1. ConnectableFlux操作符（将冷流进行多播到多个订阅者，并提供缓冲），ConnectableFlux可以实现数据缓存.

2. cache操作符（内部使用了ConnectableFlux）能实现事件缓存，并且能调整缓存容量和到期时间。

3. share操作符会为每一个订阅者传播订阅者尚未错过的事件。