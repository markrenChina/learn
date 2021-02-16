# 线程调度

1. publishOn

能将部分运行时，指定工作单元（不等于线程）。

在底层publishOn操作符会保留一个队列，并为队列提供新元素，以便专用工作单元（不是线程）消费并处理（串行）。但是别 publishOn切割的2部分线程发生了并行。（Rxjava在安卓应用中要经常切割阻塞）

```java
Scheduler s = Schedulers.newParallel("parallel-scheduler", 4); 

final Flux<String> flux = Flux
    .range(1, 2)
    .map(i -> 10 + i)  
    .publishOn(s)  
    .map(i -> "value " + i);  

new Thread(() -> flux.subscribe(System.out::println));  
```
2. subscribeOn

提供一种在订阅时指定工作单元的方法。无论将其放置subscribeOn在链中的什么位置,它始终会影响源发射的环境,publishOn它们仍会在其后的链中部分切换执行上下文

底层：subscribeOn将父Publisher的订阅放在Runnable（Scheduler调度器）中执行。

```java
Scheduler s = Schedulers.newParallel("parallel-scheduler", 4); 

final Flux<String> flux = Flux
    .range(1, 2)
    .map(i -> 10 + i)  
    .subscribeOn(s)  
    .map(i -> "value " + i);  

new Thread(() -> flux.subscribe(System.out::println));
```
3. parallel

将流分割成子流(ParallelFlux一组Flux的抽象)，并均衡他们之间的元素（此方法不会使工作并行化。而是将工作负载划分为“ rails”（默认情况下，与CPU内核一样多的rails））。

必须使用runOn(Scheduler)。需要注意的是有一个推荐的专用Scheduler并行工作：Schedulers.parallel()。

如果在并行处理序列后想要恢复为“正常” Flux并以顺序方式应用其余运算符链，则可以使用 sequential()on方法

subscribe(Subscriber<T>)合并所有导轨，同时 subscribe(Consumer<T>)运行所有导轨。如果该subscribe()方法具有lambda，则每个lambda的执行次数将与rails一样多

您也可以Flux<GroupedFlux<T>>通过groups()方法访问单个导轨或“组”，并通过该 方法向它们应用其他运算符composeGroup()

```java
Flux.range(1, 10)
//我们强制使用多个导轨，而不是依赖于CPU内核的数量。
    .parallel(2)
    .runOn(Schedulers.parallel())
    .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
```


4. Schedulers调度器 Scheduler.schedule,Scheduler.createWorker

Scheduler.schedule接口对应Runnable（工作单元），Scheduler.createWorker对应worker时Thread或资源的抽象。有3个常用实现类：

一、 SingleScheduler能为一个专用工作单元安排所有可能的任务，可以延迟安排定期事件（Schedulers.single()）。

二、 ParallelScheduler 适合CPU密集型，也可处理时间调度（Schedulers.parallel()）。

三、 ElasticScheduler （I/O密集型）动态创建并缓存的线程池（Schedulers.elastic()）


附：
Schedulers 类有给访问以下执行上下文的静态方法：

· 无执行上下文（Schedulers.immediate()）：在处理时，Runnable 将直接执行所提交的文件，从而有效地在当前文件上运行它们Thread（可以视为“空对象”或no-op Scheduler）。

· 单个可重用线程（Schedulers.single()）。请注意，此方法对所有调用方都使用相同的线程，直到调度程序被释放为止。如果您需要一个每次调用专用线程，请Schedulers.newSingle()为每个调用使用。

· 无限制的弹性线程池（Schedulers.elastic()）。引入时Schedulers.boundedElastic()，不再首选该线程，因为它倾向于隐藏背压问题并导致线程过多。

· 有界的弹性线程池（Schedulers.boundedElastic()）。像其前身一样elastic()，它根据需要创建新的工作池，并重用空闲的工作池。闲置时间过长（默认值为60s）的工作池也将被丢弃。与之前的elastic()版本不同，它对可创建的支持线程数有上限（默认为CPU内核数x 10）。达到上限后，最多可提交10万个任务，并在线程可用时重新调度（当延迟调度时，延迟在线程可用时开始）。这是I / O阻止工作的更好选择。 Schedulers.boundedElastic()是一种为阻塞进程分配自己的线程的简便方法，这样它就不会占用其他资源。请参阅如何包装同步阻止呼叫？，但使用新线程不会对系统造成太大压力。

· 已调整为并行工作的固定工作人员（Schedulers.parallel()）。它创建的工作线程数量与CPU内核数量一样多。

此外，您可以使用来创建一个Scheduler不存在ExecutorService的对象Schedulers.fromExecutorService(ExecutorService)。（Executor尽管不建议这样做，也可以从中创建一个 。）

您还可以使用这些newXXX 方法来创建各种调度程序类型的新实例。例如，Schedulers.newParallel(yourScheduleName)创建一个名为的新并行调度程序yourScheduleName。

通过Schedulers.Factory该类可以更改默认调度程序。默认情况下，a通过相似命名的方法Factory创建所有标准Scheduler。您可以使用自定义实现覆盖其中的每一个。创建Factory适合您需求的软件后，您必须通过调用进行安装 Schedulers.setFactory(Factory)。

此外，工厂还公开了另一种自定义方法： decorateExecutorService。在创建每个Scheduler由ScheduledExecutorService（作为非默认实例，例如通过调用创建的实例）支持的Reactor Core的过程中调用它 Schedulers.newParallel()。

还有最后一个可定制的挂钩Schedulers：onHandleError。每当Runnable提交给Scheduler引发任务的任务抛出异常时，都会调用此挂钩Exception（请注意，如果有运行该任务的UncaughtExceptionHandler集合，则将Thread同时调用处理程序和挂钩）。

5. Context

Java API 不保证TreadLocal的完全一致性，所以需要context。生命周期订阅时，CoreSubscriber接口（Subscriber接口的扩展）将context作为其字段进行传递，并且因为，Subscriber是从底部往顶部产生的，最早Publisher拥有最全的context状态。
