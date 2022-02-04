package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Flows;
import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;
import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * 只订阅较快产生的结果的流
 */
public class AmbDemo {

    public static void main(String[] args) {
        var fast = Flows.flow10.map(i -> "fast " + i)
                .take(5)
                .doOnSubscribe(() -> log("subscribe fast"))
                .doOnUnsubscribe(() -> log("unsubscribe fast"))
                .startWith("null fast")
                ;
        var slow = Flows.flow12.map(i -> "slow " + i)
                .take(5)
                .doOnSubscribe(() -> log("subscribe slow"))
                .doOnUnsubscribe(() -> log("unsubscribe slow"))
                .startWith("null slow")
                ;

        Observable.amb(slow,fast).subscribe(Utils::log);
        sleep(5, TimeUnit.SECONDS);
    }
}
