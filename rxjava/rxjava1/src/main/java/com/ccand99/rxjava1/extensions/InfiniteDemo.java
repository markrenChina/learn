package com.ccand99.rxjava1.extensions;

import com.ccand99.rxjava1.base.Utils;
import org.w3c.dom.ls.LSOutput;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 无限流 示例
 */
public class InfiniteDemo {

    public static void main(String[] args) {
        //create();
        Subscription delayed = delayed("delayed").subscribe(Utils::log);
        //unsubscribe 会触发thread::interrupt 主动打断流
        delayed.unsubscribe();
    }

    private static void create() {
        Observable<BigInteger> naturalNumbers = Observable.create(
                subscriber -> {
                    Runnable r = () -> {
                        BigInteger i = BigInteger.ZERO;
                        while (!subscriber.isUnsubscribed()) {
                            //TimeUnit.SECONDS.sleep(1);
                            subscriber.onNext(i);
                            i = i.add(BigInteger.ONE);
                        }
                    };
                    new Thread(r).start();
                }
        );
        naturalNumbers.subscribe(System.out::println);
    }

    //延迟执行
    private static <T> Observable<T> delayed(T x) {
        return Observable.create(
                subscriber -> {
                    Runnable r = () -> {
                        //正确的做法，应该catch错误然后通过onError传播
                        sleep(1, SECONDS);
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(x);
                            subscriber.onCompleted();
                        }
                    };
                    final Thread thread = new Thread(r);
                    thread.start();
                    //显式取消请求会执行
                    // subscriber.add(Subscriptions.create(() -> System.out.println("主动取消")));
                    subscriber.add(Subscriptions.create(thread::interrupt));
                }
        );
    }



}
