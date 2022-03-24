package com.ccand99.rxjava1.scheduler;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;

public class TestSchedulerDemo {

    static TestScheduler scheduler = Schedulers.test();
    static Observable<String> fast = Observable
            .interval(10, TimeUnit.MILLISECONDS,scheduler)
            .map(x -> "F" +x)
            .take(3);
    static Observable<String> slow = Observable
            .interval(50,TimeUnit.MILLISECONDS,scheduler)
            .map(x -> "S" + x);

    public static void main(String[] args) {
        Observable<String> stream = Observable.concat(fast,slow);
        stream.subscribe(System.out::println);
        System.out.println("Subscribed");

        //三次休眠只是为了证明TestScheduler的时间是被冻结的
        sleep(1,TimeUnit.SECONDS);
        System.out.println("After one second");
        //推进时间到25毫秒
        scheduler.advanceTimeBy(25,TimeUnit.MILLISECONDS);

        sleep(1,TimeUnit.SECONDS);
        System.out.println("After one more second");
        //再推进75毫秒到100毫秒
        scheduler.advanceTimeBy(75,TimeUnit.MILLISECONDS);

        sleep(1,TimeUnit.SECONDS);
        System.out.println("...and one more second");
        //将时间绝对值推进至200毫秒
        scheduler.advanceTimeTo(200,TimeUnit.MILLISECONDS);
    }

}
