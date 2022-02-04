package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Flows;
import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * CombineLatest
 * 不同于zip，任何一个流只有发射元素，就用其他流最新的值组合成新的元素，发送到下游。
 */
public class CombineLatestDemo {

    public static void main(String[] args) {
        var flow1 = Flows.flow10.map(i -> "flow1 " + i).take(3);
        var flow2 = Flows.flow12.map(i -> "flow2 " + i).take(3);
        Observable.combineLatest(flow1,flow2,(e1,e2) -> e1 +" " +e2)
                .subscribe(Utils::log);
        sleep(3, TimeUnit.SECONDS);
    }
}
