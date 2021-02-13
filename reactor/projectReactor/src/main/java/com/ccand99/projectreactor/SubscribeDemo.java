package com.ccand99.projectreactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class SubscribeDemo {

    public static void main(String[] args) throws InterruptedException {
        //simpleDemo();
        //handleDemo();
        //dispoableDemo();
        //customSubscriber();
        baseSubscriber();
    }

    //简单的订阅无界数据（如果处理有界数据请求，建议使用订阅对象或应用限制操作符来控制）
    static void simpleDemo() {
        Flux.just("A", "B", "C")
                .subscribe(
                        data -> System.out.println("onNext " + data),
                        error -> System.out.println("error"),
                        () -> System.out.println("onComplete")
                );
    }

    //手动控制订阅  因为主动取消收不到onComplete
    static void handleDemo() {
        Flux.range(1, 100)
                .subscribe(
                        data -> System.out.println("onNext " + data),
                        error -> System.out.println("error"),
                        () -> System.out.println("onComplete"),
                        subscription -> {
                            subscription.request(4);
                            subscription.cancel();
                        }
                );
    }

    //通过disposable取消
    static void dispoableDemo() throws InterruptedException {
        Disposable disposable = Flux.interval(Duration.ofMillis(50))
                .subscribe(
                        data -> System.out.println("onNext: " + data)
                );
        Thread.sleep(200);
        disposable.dispose();
    }

    //通过自定义Subscriber订阅  不推荐做法，因为打破了线性代码流和自己管理背压和实现订阅者的所有TCK
    static void customSubscriber(){
        Subscriber<String> subscriber = new Subscriber<String>() {
            volatile Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                System.out.println("request 1 element");
                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s + " and request 1 element");
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError" + t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        Flux<String> stringFlux = Flux.just("Hello ", "World", "!");
        stringFlux.subscribe(subscriber);
    }

    //BaseSubscriber 已经实现了TCK要求，只要重写部分方法即可。
    static void baseSubscriber() {
        Flux<String> stringFlux = Flux.just("Hello ", "World", "!");
        stringFlux.subscribe(new MySubscriber<String>());
    }

    //使用Project Reactor 提供的BaseSubscriber类
    static class MySubscriber<T> extends BaseSubscriber<T> {
        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            System.out.println("initial request for 1 element");
            request(1);
        }

        @Override
        protected void hookOnNext(T value) {
            System.out.println("onNext "+ value);
            request(1);
        }
    }
}
