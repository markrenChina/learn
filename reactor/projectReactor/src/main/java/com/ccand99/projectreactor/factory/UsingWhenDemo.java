package com.ccand99.projectreactor.factory;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.function.BiFunction;

public class UsingWhenDemo {

    private static final Logger log = LoggerFactory.getLogger(UsingWhenDemo.class);

    public static void main(String[] args) throws InterruptedException {

        Flux.usingWhen(
                Transaction.beginTransaction(),
                transaction -> transaction.insertRows(Flux.just("A", "B", "C")),
                Transaction::commit,
                new BiFunction<Transaction, Throwable, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Transaction transaction, Throwable throwable) {
                        transaction.rollback();
                        log.info("error {}",throwable.getMessage());
                        return Mono.empty();
                    }
                },
                Transaction::rollback
        ).subscribe(
                d -> log.info("onNext: {}",d),
                e -> log.info("onError: {}",e),
                () -> log.info("onComplete")
        );
        Thread.sleep(1000);
    }

}
//简化的事务处理模型
class Transaction {

    private static final Logger log = LoggerFactory.getLogger(Transaction.class);

    private static final Random random = new Random();

    private final int id;

    public Transaction(int id) {
        this.id = id;
        log.info("[T: {}] created",id);
    }

    //静态方法，生成事务
    public static Mono<Transaction> beginTransaction() {
        return Mono.defer(
                () -> Mono.just(new Transaction(random.nextInt(1000)))
        );
    }

    public Flux<String> insertRows(Publisher<String> rows) {
        //模拟插入，利用随机模拟产生插入失败的行为。
        return Flux.from(rows)
                .delayElements(Duration.ofMillis(100))
                .flatMap( r-> {
                    if (random.nextInt(10) < 2) {
                        return Mono.error(new RuntimeException("Error: "+ r) );
                    } else {
                        return Mono.just(r);
                    }
                });
    }

    //异步提交，有时提交失败
    public Mono<Void> commit() {
        return Mono.defer( () -> {
            log.info("[T: {}] commit",id);
            if (random.nextBoolean()) {
                return Mono.empty();
            } else {
                return Mono.error(new RuntimeException("conflict"));
            }
        });
    }

    //异步回滚，有时事务回滚失败
    public Mono<Void> rollback() {
        return Mono.defer( () -> {
            log.info("[T: {}] rollback",id);
            if (random.nextBoolean()) {
                return Mono.empty();
            } else {
                return Mono.error(new RuntimeException("conflict"));
            }
        });
    }
}
