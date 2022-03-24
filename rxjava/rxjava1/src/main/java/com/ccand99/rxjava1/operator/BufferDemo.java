package com.ccand99.rxjava1.operator;

import rx.Observable;

public class BufferDemo {

    public static void main(String[] args) {
        Observable.range(1,7)
                .buffer(3,1)
                .subscribe(System.out::println);
    }
}
