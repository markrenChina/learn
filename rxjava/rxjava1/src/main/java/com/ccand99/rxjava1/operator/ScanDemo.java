package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

import java.math.BigInteger;

import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * scan有2个参数，一个是生产的值，一个是上次事件产生的值。有一个重载版本可以提供初始值。
 */
public class ScanDemo {

    public static void main(String[] args) {
        Observable<BigInteger> foo = Observable
                .range(2,5)
                .scan(BigInteger.ONE,(total,chunk) -> total.add(BigInteger.valueOf(chunk)));
        foo.subscribe(Utils::log);
        //sleep(1,);
    }
}
