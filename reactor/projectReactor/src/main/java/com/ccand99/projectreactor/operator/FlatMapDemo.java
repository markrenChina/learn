package com.ccand99.projectreactor.operator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

//非常重要
public class FlatMapDemo {

    private static Logger log = LoggerFactory.getLogger(FlatMapDemo.class);
    //flatmap可以理解为map+merge两个组合，先转换再返回一个新的流。
    //不同的变体有： FlatMapSequential 和 concatMap
    //flatmap和FlatMapSequential会立即订阅，concatMap会等待全部完成再执行子流订阅。
    //concatMap保留源元素相同的顺序，FlatMapSequential会排序，flatmap不一定
    //flatmap允许子流元素交错，FlatMapSequential和concatMap不允许。
    static void flatmap() throws InterruptedException {
        Flux.just("user-1", "user-2", "user-3")
                .flatMap(u -> requestBooks(u)
                        .map(b -> u + "/" + b))
                .subscribe(res -> log.info(" onNext: {}",res));
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException {
        flatmap();
    }


    public static Flux<String> requestBooks(String user) {
        return Flux.range(1, new Random().nextInt(3) + 1)
                .map(integer -> "book-" + integer)
                .delayElements(Duration.ofMillis(3));
    }
}
