package com.ccand99.projectreactor.hotvscold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ShareDemo {

    private static Logger log = LoggerFactory.getLogger(ShareDemo.class);

    public static void main(String[] args) throws InterruptedException {
        share();
        Thread.sleep(1000);
    }

    static void share() throws InterruptedException {
        Flux<Integer> soure = Flux.range(0,5)
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(s ->
                        log.info("new subscription for the cold publisher"));

        Flux<Integer> shareSource = soure.share();

        shareSource.subscribe( e -> log.info("[Subscriber 1] onNext: {}",e));
        Thread.sleep(400);
        shareSource.subscribe( e -> log.info("[Subscriber 2] onNext: {}",e));
    }
}
