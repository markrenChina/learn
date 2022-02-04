package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

/**
 * 去重，比较是通过equals和hashcode来完成的。
 */
public class Distinct {

    public static void main(String[] args) {
        Observable.range(1,10)
                .map(Utils::random)
                .distinct()
                .subscribe(Utils::log);
    }
}
