package com.ccand99.projectreactor.handleError;

import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BadDemo {

    public static void main(String[] args) throws InterruptedException {
        handle();
        Thread.sleep(8000);
    }

    static void handle() {
        Flux.just("user-1")
                .flatMap(user ->
                        recommendedBooks(user).retryWhen(Retry.backoff(5, Duration.ofMillis(100)))
                                .timeout(Duration.ofSeconds(4))
                                .onErrorResume(e -> Flux.just("The Martian"))
                )
                .subscribe(System.out::println,System.out::println,System.out::println);
    }

    static Flux<String> recommendedBooks(String userId) {
        return Flux.defer(
                () -> {
                    if (new Random().nextInt(10) < 7) {
                        return Flux.<String>error(new RuntimeException("err"))
                                .delaySequence(Duration.ofMillis(100));
                    } else {
                        return Flux.just("Blue Mars", "The Expanse")
                                .delayElements(Duration.ofMillis(50));
                    }
                }).doOnSubscribe(s -> System.out.println(userId));
    }

    //官网retry示例：
    static void offcial() {
        AtomicInteger errorCount = new AtomicInteger();
        AtomicInteger transientHelper = new AtomicInteger();
        Flux<Integer> transientFlux = Flux.<Integer>generate(sink -> {
            int i = transientHelper.getAndIncrement();
            if (i == 10) {
                //	我们generate的消息源中含有大量错误。当计数器达到10时，它将成功完成
                sink.next(i);
                sink.complete();
            }
            else if (i % 3 == 0) {
                // 如果transientHelper原子是的倍数3，我们将发射onNext并结束当前的脉冲串。
                sink.next(i);
            }
            else {
                // 	在其他情况下，我们发出onError。那是三分之二，所以2的爆发onError被1中断onNext
                sink.error(new IllegalStateException("Transient error at " + i));
            }
        })
                .doOnError(e -> errorCount.incrementAndGet());

        //如果没有transientErrors(true)，2第二个脉冲串将达到配置的最大尝试次数，并且发出该序列后将失败onNext(3)。
        transientFlux.retryWhen(Retry.max(2).transientErrors(true))
                .blockLast();
        //assertThat(errorCount).hasValue(6);
    }
}
