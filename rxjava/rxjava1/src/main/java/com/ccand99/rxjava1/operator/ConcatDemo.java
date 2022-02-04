package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;
import static com.ccand99.rxjava1.base.Utils.sleep;

public class ConcatDemo {

    private static Observable<Integer> fromCache = Observable.just(1)
            .doOnSubscribe(() -> log("subscribe fromCache"))
            .doOnUnsubscribe(() -> log("unsubscribe fromCache"))
            .delay(10,TimeUnit.MILLISECONDS)
            .take(0);
    private static Observable<Integer> fromDb = Observable.just(123)
            .delay(1, TimeUnit.SECONDS)
            .doOnSubscribe(() -> log("subscribe fromDb"))
            .doOnUnsubscribe(() -> log("unsubscribe fromDb"));

    public static void main(String[] args) {
        Observable.concat(fromCache,fromDb).subscribe(Utils::log);
        sleep(1,TimeUnit.SECONDS);
    }
}
