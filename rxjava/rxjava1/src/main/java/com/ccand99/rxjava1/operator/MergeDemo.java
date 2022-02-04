package com.ccand99.rxjava1.operator;

import rx.Observable;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;
import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * merge 示例
 */
public class MergeDemo {

    //模拟三种算法，不同的执行时间
    private static final Observable<Integer> algorithm1 = Observable.just(1).delay(10, TimeUnit.SECONDS);
    private static final Observable<Integer> algorithm2 = Observable.just(2).delay(20, TimeUnit.SECONDS);
    private static final Observable<Integer> algorithm3 = Observable.just(3).delay(30, TimeUnit.SECONDS);

    public static void main(String[] args) {
        Instant start = Instant.now();
        log(start);
        Observable.merge(algorithm3,algorithm2,algorithm1).subscribe( e -> log(Duration.between(start,Instant.now())));
        Observable.mergeDelayError(algorithm3,algorithm2,algorithm1).subscribe( e -> log(Duration.between(start,Instant.now())));
        sleep(60,TimeUnit.SECONDS);
    }
}
