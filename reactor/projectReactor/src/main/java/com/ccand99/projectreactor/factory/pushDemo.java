package com.ccand99.projectreactor.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;

public class pushDemo {

    private static final Logger log = LoggerFactory.getLogger(pushDemo.class);

    public static void main(String[] args) throws InterruptedException {
        push();
        Thread.sleep(3000);
    }

    static void push() {
        //IntStream.range(2000,3000) Stream API 生成1000个整数
        //emitter 为FluxSink<T>类型，next发送到Flux实例
        //push 方法不需要关心背压和取消
        Flux.push(emitter -> IntStream.range(2000,3000).forEach(emitter::next))
                //延迟模拟背压
                .delayElements(Duration.ofMillis(1))
                .subscribe(e -> log.info("onNext: {}",e));
    }
}
