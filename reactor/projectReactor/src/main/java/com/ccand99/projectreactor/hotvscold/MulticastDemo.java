package com.ccand99.projectreactor.hotvscold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class MulticastDemo {

    private static Logger log = LoggerFactory.getLogger(MulticastDemo.class);

    public static void main(String[] args) {
        connectableFlux();
    }

    static void connectableFlux() {
        Flux<Integer> source = Flux.range(0, 3)
                .doOnSubscribe( s -> log.info("new subscription for the cold publisher"));

        ConnectableFlux<Integer> conn = source.publish();

        conn.subscribe( e -> log.info("[Subscriber 1] onNext: {}",e));
        conn.subscribe( e -> log.info("[Subscriber 2] onNext: {}",e));
        log.info("all subscribers are ready, connecting");

        conn.connect();
    }
}
