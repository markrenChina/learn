package com.ccand99.awesome;

public interface Publisher<T> {
    void subscribe(Subscriber<? super  T> subscriber);
}
