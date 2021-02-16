package com.ccand99.projectreactor.hotvscold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

/**
 * ConnectableFlux API中涵盖了两种主要模式，这些模式返回ConnectableFlux：publish和replay。
 * publish通过将这些请求转发到源，动态地尝试在背压方面尊重其各个订户的需求。最值得注意的是，如果任何订户的需求待定0，发布会暂停其对源的请求。
 * replay缓冲通过第一个订阅看到的数据，直至可配置的限制（时间和缓冲区大小）。它将数据重放给后续的订户
 */
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

        //connect()只要您订阅了足够的，便可以手动调用Flux。这将触发对上游源的订阅
        conn.connect();

        //autoConnect(n)n订阅后可以自动执行相同的工作。
        //ConnectableFlux<Integer> conn = source.publish().autoConnect(2);

        //refCount(n)不仅会自动跟踪传入的订阅，还会检测何时取消这些订阅。如果跟踪的订户不足，则源将“断开连接”，如果出现其他订户，则会在以后导致对该源的新订户

        //refCount(int, Duration)添加了“宽限期”。一旦被跟踪的订户数量太少，它将Duration在断开源连接之前等待，可能允许足够的新订户进入并再次超过连接阈值。
    }
}
