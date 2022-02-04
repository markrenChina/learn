package com.ccand99.rxjava1.base;

import rx.Observable;

import java.util.concurrent.TimeUnit;

public class Flows {

    public static final Observable<Long> flow1 = Observable.interval(1, TimeUnit.MILLISECONDS);
    public static final Observable<Long> flow5 = Observable.interval(5, TimeUnit.MILLISECONDS);
    public static final Observable<Long> flow10 = Observable.interval(10, TimeUnit.MILLISECONDS);
    public static final Observable<Long> flow12 = Observable.interval(12, TimeUnit.MILLISECONDS);
    public static final Observable<Long> flow20 = Observable.interval(20, TimeUnit.MILLISECONDS);

}

