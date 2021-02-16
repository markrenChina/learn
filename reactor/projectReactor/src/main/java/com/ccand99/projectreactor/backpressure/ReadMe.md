# 背压处理

应该结合批处理，也就是推拉混合，避免多次request（1）

1. onBackPressureBuffer 操作符会请求无界请求。提供队列缓冲，重载和许多配置选项，调整行为。

2. onBackPressureDrop 请求无界需求（Integer.MAX_VALUE），但是溢出丢弃，可以自定义处理丢弃元素。

3. onBackPressureLast 丢弃，但是接受最新数据。

4. onBackPressureError 下游无法跟上，抛出错误。

limitRequest（n）会限制下游消费者的（总）需求。

limitRate(N)拆分下游请求，以便将它 们以较小的批次传播到上游。