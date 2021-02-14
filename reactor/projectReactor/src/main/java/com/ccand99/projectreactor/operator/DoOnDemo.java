package com.ccand99.projectreactor.operator;

import reactor.core.publisher.Flux;

public class DoOnDemo {

    public static void main(String[] args) {
        //doOnEach();
        transition();
    }

    //doOnEach 处理onError,onSubscribe,onNext,onComplete。
    static void doOnEach() {
        Flux.just(1,2,3)
                .concatWith(Flux.error(new RuntimeException("error message")))
                .doOnEach(System.out::println)
                .subscribe();
    }

    static void transition() {
        Flux.range(3,3)
                .doOnNext(System.out::println)//这里是元素
                .materialize()
                .doOnNext(System.out::println)//这是信号，事件 onComplete也会被打印
                .dematerialize()
                .collectList()
                .subscribe(System.out::println);
    }
}
