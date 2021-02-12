package com.ccand99.awesome;

/**
 * 订阅契约
 */
public interface Subscription {
    //背压
    void request(long n);
    void cancel();
}
