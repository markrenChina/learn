package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

import java.math.BigInteger;

/**
 * reduce 扫描
 */
public class ReduceDemo {

    public static void main(String[] args) {
        Observable<BigInteger> foo = Observable
                .range(2,5)
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE,BigInteger::add);
        foo.subscribe(Utils::log);
    }
}
