package com.ccand99.rxjava1.operator;

import rx.Observable;

/**
 * 一个非纯粹的操作符，可以在不接触元素的情况下进行查看。
 * doOnNext只是简单的接受上游事件并将其传递给下游，它不能以任何形式进行修改。
 * doOnNext的行为类似探针，这是侦听（wiretap）模式的简单实现。
 */
public class DoOnNextDemo {

    public static void main(String[] args) {
        Observable.just(8,9,10)
                .doOnNext(e -> System.out.println("A: "+ e))
                .filter( i -> i % 3 > 0)
                .doOnNext(e -> System.out.println("B: " + e))
                .map( i -> "#" + i * 10)
                .doOnNext(e -> System.out.println("C: " + e))
                .filter(s -> s.length() < 4)
                .subscribe(e -> System.out.println("D: " + e));
    }
}
