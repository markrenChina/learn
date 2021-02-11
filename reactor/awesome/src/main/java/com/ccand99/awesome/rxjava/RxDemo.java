package com.ccand99.awesome.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 入门示例，v1.2.7已弃用这种创建，因为不支持背压
 * 这里使用rxjava3.0库
 */
public class RxDemo {

    public static void main(String[] args) throws InterruptedException {
        //假设Observable是一种知道如何在订阅时为订阅者传播事件的生成器，示例：
        Observable<String> observable = Observable.create(
                new ObservableOnSubscribe<String>() {
                    //订阅者出现时立即触发
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        //产生一串字符串，
                        emitter.onNext("Hello,reactive world!");
                        //发送流结束信号
                        emitter.onComplete();
                    }
                }
        );

        //3.0使用消费者（Consumer）对应Subscriber，后者设计接口跟我们写的RxObserver类似
        //onComplete 和onError 成为订阅的二参和三参，都能直接以函数式传入
        //或者subscriber改为Observer接口
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                System.out.println(s);
            }
        };

        Disposable subscribe = observable.subscribe(consumer);

        //lambda改进
        Observable.create(
                emitter -> {
                    emitter.onNext("Hello,reactive world!");
                    emitter.onComplete();
                }
        ).subscribe(
                System.out::println
        );

        //创建Observable方式：
        Observable.just("1","2","3");
        Observable.fromArray("A","B","C");
        Observable.fromIterable(Collections.emptyList());
        //回调转换
        Observable<String> hello = Observable.fromCallable( () -> "hello" );
        //Future转换
        Future<String> future = Executors.newCachedThreadPool().submit( () -> "world");
        Observable<String> world = Observable.fromFuture(future);

        //concat创建
        Observable.concat(hello,world,Observable.just("!")).forEach(System.out::print);

        //基于时间间隔生成一个异步事件序列（安卓jetpack工具库里暂时没有能这么方便的实现方式）
        Disposable subscribe2 = Observable.interval(1, TimeUnit.SECONDS).subscribe(e -> System.out.println("Received: " + e));
        Thread.sleep(5000);
        subscribe2.dispose();

        CountDownLatch externalSignal = new CountDownLatch(5);
        //Disposable 对应 Subscription
        Disposable subscribe1 = Observable.interval(100, TimeUnit.MILLISECONDS).subscribe(System.out::println);
        externalSignal.await();
        //对应 Subscription.unsubscribe();
        subscribe1.dispose();


    }
}
