package com.ccand99.rxjava1.scenarios;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;
import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * 并发示例
 */
public class AsyncDemo {

    private static Observable<String> foo() {
        return Observable.just("hello").delay(3, TimeUnit.SECONDS)
                .doOnSubscribe(() -> log("foo doOnSubscribe" + Instant.now()))
                .doOnUnsubscribe(() -> log("foo doOnUnsubscribe" + Instant.now()));
    }

    private static Observable<String> bar() {
        return Observable.just("world").delay(1, TimeUnit.SECONDS)
                .doOnSubscribe(() -> log("bar doOnSubscribe" + Instant.now()))
                .doOnUnsubscribe(() -> log("bar doOnUnsubscribe" + Instant.now()));
    }

    public static void main(String[] args) {
        foo().subscribeOn(Schedulers.io()).zipWith(
                bar().subscribeOn(Schedulers.io()),
                (f,b) -> f+" "+b
        ).map( s ->{
                    Utils.log(s + Instant.now());
                    return s;
                }
        ).delay(1,TimeUnit.SECONDS).subscribe(Utils::log);
        sleep(5,TimeUnit.SECONDS);
    }
}
