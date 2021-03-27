package com.ccand99.projectreactor.stepverifier;

import org.reactivestreams.Publisher;
import org.springframework.util.Assert;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class SuperUse {

    public static void main(String[] args) {
        //eternity();
        //backpressure();
        newEvent();
    }

    //对于无界Publisher的验证 不取消会一直阻塞
    private static void eternity() {
        Flux<String> websocketPublisher = vmWebsocket();
        var res =StepVerifier.create(websocketPublisher)
                .expectSubscription()
                .expectNextCount(12)
                .expectNext("Price: $12.00")
                .thenCancel()
                .verify();
        System.out.println(res);
    }

    private static Flux<String> vmWebsocket() {
        return Flux.interval(Duration.ofMillis(10)).map(e -> "Price: $"+e+".00").take(20);
    }

    //验证背压行为 .thenRequest
    private static void backpressure() {
        Flux<String> websocketPublisher = vmWebsocket();
        //默认需求0
        var res = StepVerifier
                .create(websocketPublisher.onBackpressureBuffer(5),0)
                .expectSubscription()
                .thenRequest(1)
                .expectNext("Price: $0.00")
                .thenRequest(1)
                .expectNext("Price: $1.00")
                .expectError(reactor.core.Exceptions.failWithOverflow().getClass())
                .verify();
        System.out.println(res);
    }

    //触发新的事件
    private static void newEvent() {
        TestPublisher<String> idsPublisher = TestPublisher.create();

        var res = StepVerifier.create(findAllById(idsPublisher))
                .expectSubscription()
                .then(()-> idsPublisher.next("1"))
                .assertNext(w -> assertThat(w,hasProperty("id",equalTo("1"))))
                .then(() -> idsPublisher.next("2"))
                .assertNext(w -> assertThat(w,hasProperty("id",equalTo("2"))))
                .then(idsPublisher::complete)
                .expectComplete()
                .verify();
        System.out.println(res);

    }

    //模拟数据操作
    private static Flux<Wallet> findAllById(Publisher<String> idPublisher){
        Assert.notNull(idPublisher, "The Id Publisher must not be null!");

        /*return Flux.from(idPublisher).buffer().filter(ids -> !ids.isEmpty()).concatMap(ids -> {
            if (ids.isEmpty()) {
                return Flux.empty();
            }
            String idProperty = getIdProperty().getName();
            return this.entityOperations.select(Query.query(Criteria.where(idProperty).in(ids)), this.entity.getJavaType());
        });*/
        return Flux.just(Wallet.wallet(1,"admin",0));
    }
}
