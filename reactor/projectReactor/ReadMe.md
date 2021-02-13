Flux和Mono
响应式规范中Publisher<T>的实现类

Flux可以产生0..N个元素（无限流）

Mono最多生成一个元素（可以轻松的替换CompletableFuture<T>）

Mono对比Flux 跳过了冗余缓冲区和昂贵的同步。

```java
Mono.from(Flux.from(mono))
```
上述代码实际返回原始mono，并不会执行转换。

Rxjava2与ProjectReactor

1. Observable：  Rxjava2对比Rxjava1不再接受null。Observable既不实现背压，也不实现Publisher（响应式规范），不推荐作用与大量元素，但其开销小于Flowable、具有toFlowable方法，转换需要选择背压策略。

2. Flowable：  直接对应Flux，实现了Publisher。

3. Single：  生成且仅生成一个元素的流，不继承Publisher，使用toFlowable不要背压策略。对比mono更接近CompletableFuture，但是依然是不订阅不执行。

4. Maybe：  与Mono类型具有相同的语义，但是不实现Publisher，需要时使用toFlowable

5. Completable： 只能触发onError或onComplete，不能产生onNext，没有实现Publisher接口，toFlowable方法不能生成带onNext的Mono<void>类型。

defer工厂方法：
会创建一个序列，并在订阅时决定其行为。
```java
Mono<User> requestUserData(String sessionId) {
    return Mono.defer( ()->
        isValidSession(sessionId)         
        ? Mono.fromCallable( () -> requestUser(sessionId) )
        : Mono.error(new RuntimeException("Invalid user session")));
        }
```
去掉Mono.defer，会将判断执行在订阅之前。

过滤响应式序列：
先开始一个流，停止它来自另一个流的事件响应，代码：
```java
Mono<?> startCommand = ...
Mono<?> stopCommand = ...
Flux<UserEvent> streamOfData = ...

streamofData
    .skipUntilOther(startCommand)
    .takeUnitOther(stopCommand)
    .subscribe(System.out::Print)
```
弹珠图：
![img.png](img.png)
