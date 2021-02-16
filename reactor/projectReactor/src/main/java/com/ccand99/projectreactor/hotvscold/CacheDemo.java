package com.ccand99.projectreactor.hotvscold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class CacheDemo {

    private static Logger log = LoggerFactory.getLogger(CacheDemo.class);

    public static void main(String[] args) throws InterruptedException {

        cacheFlux();
    }

    static void cacheFlux() throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 2)
                .doOnSubscribe( s -> log.info("new subscription for the cold publisher"));

        //缓存冷发布者，持续时间1秒
        Flux<Integer> cacheSource = source.cache(Duration.ofSeconds(1));

        //订阅同一份资源
        cacheSource.subscribe( e -> log.info("[Subscriber 1] onNext: {}",e));
        cacheSource.subscribe( e -> log.info("[Subscriber 2] onNext: {}",e));
        Thread.sleep(1200);
        //超时，资源被清除，重新触发了新定义（控制台重新打印了 new subscription for the cold publisher）
        cacheSource.subscribe( e -> log.info("[Subscriber 3] onNext: {}",e));
    }
}
