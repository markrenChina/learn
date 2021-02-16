# 处理器
不推荐实现processor接口，容易出错。而且也是可以通过操作符组合实现相同的功能，推荐使用生成器方法。

内置几种处理器：

1. Direct 只能通过操作处理器的接收器来推送因用户手动操作而产生的数据。DirectProcessor 不处理背压，但可用于向多个订阅者发布。UnicastProcessor，用队列处理背压，但是只能为一个Subscriber服务。

2. Synchronous （EmitterProcessor,ReplayProcessor）可以同时手动订阅上游Publisher推送数据。EmitterProcessor，可以为多个订阅者服务，但仅能以同步的方式消费单一Publisher。ReplayProcessor类似，，但它能使用几种策略来缓存传入数据。

3. Asynchronous (WorkQueueProcessor，TopicProcessor) 可以推送多个上游发布者处获得的下游数据（RingBuffer结构）。TopicProcessor兼容响应式流，并可以为每个下游Subscriber关联一个Thread来处理交互。因此，服务数量有限。WorkQueueProcessor与其类似，但是放宽一些要求，运行时使用更少的资源。 