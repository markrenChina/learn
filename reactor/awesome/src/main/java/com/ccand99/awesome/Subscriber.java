package com.ccand99.awesome;

//与Rxjava的Observer接口几乎完全相同
public interface Subscriber<T> {
    //新的附加方法
    void onSubscribe(Subscription subscription);
    void onNext(T t);
    void onError(Throwable t);
    void onComplete();
}
