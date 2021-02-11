package com.ccand99.awesome.rxjava;

public interface RxObserver<T> {
    void onNext(T event);
    void onComplete();
    void onError(Exception exception);
}
