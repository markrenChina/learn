package com.ccand99.projectreactor.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

public class GenerateDemo {

    private static final Logger log = LoggerFactory.getLogger(GenerateDemo.class);

    public static void main(String[] args) throws InterruptedException {
        //generate();
        //officialDemo1();
        //officialDemo2();
        officialDemo3();
        Thread.sleep(1000);
    }

    static void generate() {
        Flux.generate(
                // 初始状态
                () -> Tuples.of(0L, 1L),
                //这里类似reduce操作符
                (state, sink) -> {
                    log.info("generated value {}", state.getT2());
                    sink.next(state.getT2());
                    long newValue = state.getT1() + state.getT2();
                    return Tuples.of(state.getT2(), newValue);
                })
                //同delayElements模拟会执行BiFunction，再执行onNext
                //.delayElements(Duration.ofMillis(1))
                .take(7)
                .subscribe(e -> log.info("onNext: {}", e));
    }

    //https://projectreactor.io/docs/core/release/reference/
    static void officialDemo1() {
        Flux<String> flux = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    log.info("generated value {}", state);
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) sink.complete();
                    return state + 1;
                });
        flux.delayElements(Duration.ofMillis(1))
                .take(7).subscribe(e -> log.info("onNext: {}", e));
    }

    //generate(Supplier<S>, BiFunction, Consumer<S>)
    static void officialDemo2() {
        Flux<String> flux = Flux.generate(
                //这次，我们生成一个可变对象作为状态
                AtomicLong::new,
                (state, sink) -> {
                    log.info("generated value {}", state);
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) sink.complete();
                    return state;
                });
        flux.take(7).subscribe(e -> log.info("onNext: {}", e));
    }

    //包含consumer
    static void officialDemo3() {
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    log.info("generated value {}", state);
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state: " + state));
        flux.take(7).subscribe(e -> log.info("onNext: {}", e));
    }
}
