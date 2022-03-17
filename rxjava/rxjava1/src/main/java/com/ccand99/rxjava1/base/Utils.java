package com.ccand99.rxjava1.base;

import rx.Observable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static void sleep(int timeOut, TimeUnit unit) {
        try {
            unit.sleep(timeOut);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("InterruptedException");
        }
    }

    //将CompletableFuture转换成只有一个元素的Observable
    static <T>Observable<T> observable(CompletableFuture<T> future){
        return Observable.create(subscriber -> {
            //whenComplete接收2个互相排斥的参数
            future.whenComplete((value, exception) -> {
                if (exception != null){
                    subscriber.onError(exception);
                } else {
                    subscriber.onNext(value);
                    subscriber.onCompleted();
                }
            });
        });
    }

    //将Observable转换为CompletableFuture
    static  <T> CompletableFuture<T> toFuture(Observable<T> observable){
        CompletableFuture<T> promise = new CompletableFuture<>();
        observable.single()//强制只能发布一个值
                .subscribe(
                        promise::complete,
                        promise::completeExceptionally
                );
        return promise;
    }

    //将Observable转换为CompletableFuture<List<T>>
    static  <T> CompletableFuture<List<T>> toFutureList(Observable<T> observable){
        return toFuture(observable.toList());
    }

    public static void log(Object obj){
        System.out.printf("Thread [%s] : %s%n",Thread.currentThread().getName(),obj);
    }

    public static Integer random(int bound){
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
