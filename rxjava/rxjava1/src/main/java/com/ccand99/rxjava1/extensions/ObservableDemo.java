package com.ccand99.rxjava1.extensions;

import com.ccand99.rxjava1.base.Tweet;
import com.ccand99.rxjava1.base.Utils;
import jdk.jshell.execution.Util;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;

/**
 * 创建 {@link rx.Observable}
 */
public class ObservableDemo {
    public static void main(String[] args) {
        //create();
        noCache();
        log("=============================");
        cache();
        log("=============================");
        justCache();
        log("=============================");
        timer();
        log("=============================");
        interval();
    }

    public static void create(){
        Observable<Integer> ints = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("create");
                subscriber.onNext(5);
                subscriber.onNext(6);
                subscriber.onNext(7);
                subscriber.onNext(8);
                log("completed");
            }
        });
        log("Starting");
        ints.subscribe(i -> log("Element: " + i));
        //测试订阅2次
        ints.subscribe(i -> log("Element2: " + i));
    }

    private static void noCache() {
        Observable<Integer> ints = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                //noCache 这里会被执行2次
                log("create");
                subscriber.onNext(5);
                subscriber.onCompleted();
            }
        });
        log("Starting");
        ints.subscribe(i -> log("Element: " + i));
        //测试订阅2次
        ints.subscribe(i -> log("Element2: " + i));
        log("Exit");
    }

    private static void cache() {
        Observable<Integer> ints = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                //cache 之后第二及之后的订阅都是直接从cache之后的操作符发射元素
                //cache 配合无限流会oom
                //这里只会执行一次
                log("create");
                subscriber.onNext(5);
                subscriber.onCompleted();
            }
        }).cache();
        log("Starting");
        ints.subscribe(i -> log("Element: " + i));
        //测试订阅2次
        ints.subscribe(i -> log("Element2: " + i));
        log("Exit");
    }

    private static void justCache() {
        Observable<Tweet> ints = Observable.just(new Tweet("a"));
        log("Starting");
        ints.subscribe(i -> log("Element: " + i));
        //测试订阅2次
        ints.subscribe(i -> log("Element2: " + i));
        log("Exit");
    }

    private static void timer() {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(Utils::log);
        Utils.sleep(2,TimeUnit.SECONDS);
    }

    //模拟刷新60Hz
    private static void interval() {
        Observable.interval(1_000_000/60,TimeUnit.MICROSECONDS)
                .subscribe(Utils::log);
        Utils.sleep(2,TimeUnit.SECONDS);
    }


}
