package com.ccand99.projectreactor.operator;

import com.ccand99.projectreactor.hotvscold.CacheDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.util.function.Tuple2;

import java.util.Random;
import java.util.function.Function;

/**
 * transform操作符类似于命令式编程的方法封装
 * 把一段流代码单独封装成一个对象，然后可以重复调用。
 *
 * 组装时间：在Flux实例上调用运算符并返回新Flux实例的时刻
 * 订阅时间：该实例的订阅时间。实际上，时刻（复数），因为可能存在多个订阅，而订阅可能相距甚远。
 * 具体组装，订阅说明，参考流，生命周期
 */
public class TransformDemo {

    private static Logger log = LoggerFactory.getLogger(TransformDemo.class);

    public static void main(String[] args) {
        //transform();
        compose();
        //distinction();
    }

    static void transform() {

        Function<Flux<String>, Flux<String>> logUserInfo = stream ->
                stream.index()
                        .doOnNext(
                                tp -> log.info("[{}] User: {}", tp.getT1(), tp.getT2())
                        ).map(Tuple2::getT2);

        Flux.range(1000, 3)
                .map(i -> "user-" + i)
                .transform(logUserInfo)
                .subscribe(e -> log.info("onNext {}", e));

        Flux.range(2000, 3)
                .map(i -> "user-" + i)
                .transformDeferred(logUserInfo)
                .subscribe(e -> log.info("onNext {}", e));
    }


    //一种惰性的执行
    static void compose() {
        Function<Flux<String>, Flux<String>> logUserInfo = (stream) -> {
            if (new Random().nextBoolean()) {
                return stream.doOnNext(e -> log.info("[path A] User: {}", e));
            } else {
                return stream.doOnNext(e -> log.info("[path B] User: {}", e));
            }
        };

        Flux<String> publisher = Flux.just("1", "2","3","4")
                //compose rename transformDeferred 2019-06-12
                .transformDeferred(logUserInfo);

        publisher.subscribe();
        publisher.subscribe();
        publisher.subscribe();
        System.out.println();
        Flux<String> publisher2 = Flux.just("1", "2","3","4","5","6")
                //compose rename transformDeferred 2019-06-12
                .transform(logUserInfo);

        publisher2.subscribe();
        publisher2.subscribe();
        publisher2.subscribe();
    }

    static void distinction() {
        int[] counter = new int[1];
        //在流中引用外部变量是不被推荐的
        Function transformer = f -> {
            log.info("ex ++ ");
            counter[0]++;
            return f;
        };

        //transform function will be executed once during assembling of the pipe, so in other words transformation function will be executed eagerly.
        //transform 调用在组装时刻 立即在组装时应用它。由于订阅者紧随其后，因此他们都“共享”该功能的相同结果。
        Flux flux = Flux.just("")
                .transform(transformer);


        System.out.println(counter[0]);

        flux.subscribe();
        flux.subscribe();
        flux.subscribe();
        System.out.println(counter[0]);

        //Which means that for each subscriber transformation function will be executed separately, and we may consider that kind of execution as lazy
        //transformDeferred 调用在订阅时刻，所有被订阅几次执行了几次。它将推迟应用Function到发生订阅的那一刻
        //可以为每个订阅生成一个不同的运算符链（通过维护某种状态）
        Flux compose = Flux.just("")
                .transformDeferred(transformer);

        System.out.println(counter[0]);

        compose.subscribe();
        compose.subscribe();
        compose.subscribe();
        System.out.println(counter[0]);
    }


}
