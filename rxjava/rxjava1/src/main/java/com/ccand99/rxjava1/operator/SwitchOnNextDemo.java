package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Flows;
import com.ccand99.rxjava1.base.Utils;
import jdk.jshell.execution.Util;
import rx.Observable;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;
import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * switchOnNext 示例
 * 先到达的流会先被订阅，后到达的流到达时，被订阅的流会被取消。switchOnNext，只会转发最后的流。
 */
public class SwitchOnNextDemo {

    //模拟内存读取
    private static Observable<String> fromCache = Observable.just("fromCache1","fromCache2","fromCache3")
            .map(s -> s + Instant.now())
            .delay(2,TimeUnit.MILLISECONDS)
            .flatMap(Observable::just,1)
            .doOnSubscribe(() -> log("subscribe fromCache"))
            .doOnUnsubscribe(() -> log("unsubscribe fromCache"));
    //模拟本地持久化读取
    private static Observable<String> fromDb = Observable.just("fromDb1","fromDb2","fromDb3")
            .map(s -> s + Instant.now())
            .delay(2,TimeUnit.MILLISECONDS)
            .flatMap(Observable::just,1)
            .doOnSubscribe(() -> log("subscribe fromDb"))
            .doOnUnsubscribe(() -> log("unsubscribe fromDb"));
    //模拟网络读取
    private static Observable<String> fromHttp = Observable.just("fromHttp1","fromHttp2","fromHttp3")
            .map(s -> s + Instant.now())
            .delay(2,TimeUnit.MILLISECONDS)
            .flatMap(Observable::just,1)
            .doOnSubscribe(() -> log("subscribe fromHttp"))
            .doOnUnsubscribe(() -> log("unsubscribe fromHttp"));

    public static void main(String[] args) {
        demo1();
    }

    public static void demo1(){
        //用flatMap delay模拟订阅延迟，如果直接可以订阅，SwitchOnNext会马上订阅
        var fromCacheObservable = Observable.just(fromCache)
                .flatMap(o -> Observable.just(o)
                        .delay(1,TimeUnit.MILLISECONDS)
                );
        var fromDbObservable = Observable.just(fromDb)
                .flatMap(o -> Observable.just(o)
                        .delay(100,TimeUnit.MILLISECONDS)
                );
        var fromHttpObservable = Observable.just(fromHttp)
                .flatMap(o -> Observable.just(o)
                        .delay(1,TimeUnit.SECONDS)
                );
        var source = Observable.merge(
                fromCacheObservable,fromDbObservable,fromHttpObservable);
        Observable.switchOnNext(source)
                .subscribe(Utils::log);
        sleep(5,TimeUnit.SECONDS);
    }

}
