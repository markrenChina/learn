Rxjava基本概念：
观察者模式的订阅者接口：
```java
public interface Observer<T> {
    void observe(T event);
}
```
观察者模式目标对象Subject，rxjava中称为Subject或Observable接口：

```java
public interface Subject<T> {
    void registerObserver(Observer<T> observer);
    void unregisterObserver(Observer<T> observer);
    void notifyObservers(T event);
}
```
原来的观察者模式无法解决，数据流结束信号和无观察状态生产事件，Rxjava引入迭代器设计模式来解决，于是订阅者接口变成了：
```java
public interface RxObserver<T> {
    void onNext(T event);
    void onComplete();
}
```
通过onNext回调将一个值通知到Rx版本的订阅者（RxObserver）,通过onComplete()通知流的结束，为了解决流中错误处理，继续引入onError():
```java
public interface RxObserver<T> {
    void onNext(T event);
    void onComplete();
    void onError(Exception exception);
}
```
### Observable 对应观察者模式的Subject

Observable拥有数百种流转方式和几十种初始化响应式流的工厂方法。

### Subscriber抽象类不仅实现Observer接口并且消费元素，还被用作Subscriber的实际实现基础。

### Observer与Subscriber之间的运行时关系由Subscription控制，Subscription可以检查订阅状态并在必要时取消它。

Rx系列操作符弹珠图： https://rxmarbles.com/

Rx系列支持自定义操作符。