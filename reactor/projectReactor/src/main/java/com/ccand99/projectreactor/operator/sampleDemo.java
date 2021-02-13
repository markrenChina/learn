package com.ccand99.projectreactor.operator;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class sampleDemo {

    //采样： 不要所有传入事件就能成功操作的场景
    static void sample() throws InterruptedException {
        Flux.range(1, 100 )
                .delayElements(Duration.ofMillis(1))
                .sample(Duration.ofMillis(20))
                .subscribe(System.out::println);
        Thread.sleep(105);
    }

    public static void main(String[] args) throws InterruptedException {
        sample();
    }
}
