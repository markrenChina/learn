package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

/**
 * collect操作，其实是将多元素的流转为单元素的流（在reactor中类似Flux转为Mono
 */
public class CollectDemo {
    public static void main(String[] args) {
        Observable.range(2,5)
                .collect(StringBuilder::new,(StringBuilder::append))
                .subscribe(Utils::log);
    }
}
