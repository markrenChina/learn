package com.ccand99.rxjava1.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;

public class TimerDemo {

    static MetricRegistry metricRegistry = new MetricRegistry();
    static ConsoleReporter reporter = ConsoleReporter
            .forRegistry(metricRegistry)
            .build();

    public static void main(String[] args) {
        reporter.start(1, TimeUnit.SECONDS);
        Timer timer = metricRegistry.timer("timer");
        Timer.Context ctx = timer.time();
        sleep(1, TimeUnit.SECONDS);
        ctx.stop();
        sleep(1, TimeUnit.SECONDS);

        Timer timer2 = metricRegistry.timer("timer2");
        Timer.Context ctx2 = timer2.time();
        Observable doSomething = Observable.just(1)
                .delay(1, TimeUnit.SECONDS);
        //defer保证延迟加载，让定时器跟接近doSomething创建
        Observable
                .defer(() -> Observable.just(timer2.time()))
                .flatMap(timerCtx -> doSomething.doOnCompleted(timerCtx::stop))
                .subscribe();
        sleep(2, TimeUnit.SECONDS);
    }
}
