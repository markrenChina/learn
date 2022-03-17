package com.ccand99.rxjava1.scheduler;

import com.ccand99.rxjava1.base.MySchedulers;
import rx.Observable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;
import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * 异步调度示例
 */
public class AsyncDemo {

    public static void main(String[] args) {
        log("Starting");
        Observable<String> obs = Observable.create(subscriber -> {
            log("subscriber");
            subscriber.onNext("A");
            subscriber.onNext("B");
            subscriber.onNext("C");
            subscriber.onNext("D");
            subscriber.onNext("E");
            subscriber.onCompleted();
        });
        log("Created");
        obs.subscribeOn(MySchedulers.schedulerA)
                .flatMap(record -> store(record).subscribeOn(MySchedulers.schedulerB))
                .observeOn(MySchedulers.schedulerC)
                .subscribe(
                        x -> log("Got: " +x),
                        Throwable::printStackTrace,
                        () ->log("Completed")
                );
        log("Exiting");
    }


    private static Observable<UUID> store(String s) {
        return Observable.create(subscriber -> {
            log("Storing " + s);
            sleep(1, TimeUnit.SECONDS);
            subscriber.onNext(UUID.randomUUID());
            subscriber.onCompleted();
        });
    }
}

//print
/*
Thread [main] : Starting
Thread [main] : Created
Thread [main] : Exiting
Thread [Sched-A-0] : subscriber
Thread [Sched-B-1] : Storing B
Thread [Sched-B-3] : Storing D
Thread [Sched-B-0] : Storing A
Thread [Sched-B-2] : Storing C
Thread [Sched-B-4] : Storing E
Thread [Sched-C-1] : Got: 3827f1c8-9f4f-4cef-8c0f-44dea577d9b1
Thread [Sched-C-1] : Got: c6386703-b69c-4811-b3c0-10468d3d1e3b
Thread [Sched-C-1] : Got: 4c73bd48-421c-49fe-9135-f17f6cb08f91
Thread [Sched-C-1] : Got: 164a5e2d-36bd-414c-8b2f-ad240a99346b
Thread [Sched-C-1] : Got: 9e09ff0e-ad2e-432f-ae33-e878f2c8c42a
Thread [Sched-C-1] : Completed
 */
