package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

/**
 * 对比上次的事件（equals），如果不想等则发送到下游，
 */
public class DistinctUntilChangedDemo {

    public static void main(String[] args) {
        Observable.just(1,1,1,2,1,2,2,2,1,1)
                .distinctUntilChanged()
                .subscribe(Utils::log);
    }
}
