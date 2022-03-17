package com.ccand99.rxjava1.scheduler;

import com.ccand99.rxjava1.base.Utils;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;
import static com.ccand99.rxjava1.base.Utils.sleep;

public class ImmediateDemo {

    static Scheduler scheduler = Schedulers.immediate();
    static Scheduler.Worker worker = scheduler.createWorker();

    public static void main(String[] args) {
        log("Main start");
        sleepOneSeconds();
        worker.schedule(() -> {
            log(" Out Start");
            sleepOneSeconds();
            worker.schedule(() ->{
                log("  Inner Start");
                sleepOneSeconds();
                log("  Inner End");
            });
            log(" Out End");
        });
        log("Main end");
        worker.unsubscribe();
    }

    public static void sleepOneSeconds(){
        sleep(1, TimeUnit.SECONDS);
    }
}
