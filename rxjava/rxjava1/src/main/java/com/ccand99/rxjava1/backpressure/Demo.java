package com.ccand99.rxjava1.backpressure;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;

public class Demo {

    //产生盘子
    static Observable<Dish> dishes = Observable
            .range(1, 1_000_000_000)
            .map(Dish::new);


    public static void main(String[] args) {

        //生产者和消费者同一个线程,消费者隐式阻塞生产者
//        dishes
//                .subscribe(x -> {
//                    System.out.println("Washing: " + x);
//                    sleep(50, TimeUnit.MILLISECONDS);
//                });
        //生产者，消费者不同线程,rx提供了智能的机制避免range生成太多事件，导致oom
//        dishes
//                .observeOn(Schedulers.io())
//                .subscribe(x -> {
//                    System.out.println("Washing: " + x);
//                    sleep(50, TimeUnit.MILLISECONDS);
//                });
//        sleep(10,TimeUnit.SECONDS);
        //背压的使用
        Observable
                .range(1,10)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        request(3);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //..
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                        request(1);
                        //..
                    }
                });

    }
}
