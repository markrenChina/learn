package com.ccand99.projectreactor.hotvscold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.UUID;

//冷流http示例
public class ColdDemo {

    private static Logger log = LoggerFactory.getLogger(ColdDemo.class);

    public static void main(String[] args) {
        http();
    }

    static void http() {
        Flux<String> coldPublisher = Flux.defer( () -> {
                    log.info("Generating new items ");
                    return Flux.just(UUID.randomUUID().toString());
                }
        );

        log.info("No data was generated so far");
        coldPublisher.subscribe( e -> log.info("onNext: {}",e));
        coldPublisher.subscribe( e -> log.info("onNext: {}",e));
        log.info("end");
    }
}
