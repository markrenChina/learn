## 错误处理

### 反应序列中的 任何错误都是终端事件。即使使用了错误处理运算符，它也不会让原始序列继续。相反，它将onError信号转换为新序列的开始（后备序列）。换句话说，它将替换其上游的终止序列。未经检查的异常总是通过传播onError.

错误处理运算符决策树：https://projectreactor.io/docs/core/release/reference/#which.errors

1. 在subscribe中的onError定义处理程序（如果未定义，则onError抛出UnsupportedOperationException。您可以使用Exceptions.isErrorCallbackNotImplemented方法进一步对其进行检测和分类。）

2. 通过onErrorReturn来捕获错误，用一个默认值或从异常出计算出的值替换，等效于try-catch

```java
Flux.just(1, 2, 0)
    .map(i -> "100 / " + i + " = " + (100 / i)) //this triggers an error with 0
    .onErrorReturn("Divided by zero :("); // error handling example
```

3. 可以用onErrorResume捕获异常，并执行备用工作流
```java
//如果您的标称进程正在从外部且不可靠的服务中获取数据，但是您还保留了同一数据的本地缓存，该缓存可能会过时但更可靠
Flux.just("key1", "key2")
        .flatMap(k -> callExternalService(k)
        .onErrorResume(e -> getFromCache(k))
        );

```
等效于：
```java
String v1;
try {
  v1 = callExternalService("key1");
}
catch (Throwable error) {
  v1 = getFromCache("key1");
}

String v2;
try {
  v2 = callExternalService("key2");
}
catch (Throwable error) {
  v2 = getFromCache("key2");
}
```
如果本是一个带异常处理的Futrue：
```java
erroringFlux.onErrorResume(error -> Mono.just( 
        MyWrapper.fromError(error) 
));
```

4. 用onErrorMap转换为另一个异常处理

```java
Flux.just("timeout1")
    .flatMap(k -> callExternalService(k))
    .onErrorMap(original -> new BusinessException("oops, SLA exceeded", original));
```

5. doOnError 运算符，等效于“捕获，记录特定于错误的消息并重新抛出”模式

```java
LongAdder failureStat = new LongAdder();
Flux<String> flux =
Flux.just("unknown")
    .flatMap(k -> callExternalService(k) 
        .doOnError(e -> {
            failureStat.increment();
            log("uh oh, falling back, service failed for key " + k); 
        })
        
    );
```
6. doFinally和using doFinally与序列终止（用onComplete或onError或取消）时要执行的副作用有关。它提示您哪种终止方式会引起副作用。using请查看工厂说明
```java
Stats stats = new Stats();
LongAdder statsCancel = new LongAdder();

Flux<String> flux =
Flux.just("foo", "bar")
    .doOnSubscribe(s -> stats.startTimer())
    .doFinally(type -> { 
        stats.stopTimerAndRecordTiming();
        if (type == SignalType.CANCEL) 
          statsCancel.increment();
    })
    .take(1); 
```

7. retry重新执行响应式流(原始流依然终止)，retryBackoff提供指数退避算法。retryWhen需要一个Flux<RetrySignal>参数（可以用Retry.from(Function)工厂方法创建）。
```java
Flux.interval(Duration.ofMillis(250))
    .map(input -> {
        if (input < 3) return "tick " + input;
        throw new RuntimeException("boom");
    })
    .retry(1)
    .elapsed() 
    .subscribe(System.out::println, System.err::println); 

Thread.sleep(2100); 
```

8. other:

去空数据流： defaultIfEmpty返回默认值，或者switchIfEmpty返回不同的响应流。

可以用timeout操作符，可以抛出TimeoutException，然后处理可以抛出TimeoutException异常

Reactor有一个Exceptions实用程序类，您可以使用它来确保仅在检查了异常的情况下包装异常：

Exceptions.propagate如有必要，使用该方法包装异常。它还throwIfFatal先调用 ，并且不包装RuntimeException。

使用该Exceptions.unwrap方法来获取原始的未包装的异常（返回到特定于反应堆的异常的层次结构的根本原因）。

```java
Flux<String> converted = Flux
    .range(1, 10)
    .map(i -> {
        try { return convert(i); }
        catch (IOException e) { throw Exceptions.propagate(e); }
    });

converted.subscribe(
v -> System.out.println("RECEIVED: " + v),
e -> {
if (Exceptions.unwrap(e) instanceof IOException) {
System.out.println("Something bad happened with I/O");
} else {
System.out.println("Something bad happened");
}
}
);

```

