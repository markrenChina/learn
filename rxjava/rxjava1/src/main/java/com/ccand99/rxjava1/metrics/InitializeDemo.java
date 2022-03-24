package com.ccand99.rxjava1.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Flows.flow1;
import static com.ccand99.rxjava1.base.Flows.flow20;
import static com.ccand99.rxjava1.base.Utils.sleep;

public class InitializeDemo {

    //各种指标的一个工厂
    static MetricRegistry metricRegistry = new MetricRegistry();
    //将统计快照推送至slf4j logger，除此之外还可以推送到Graphite(图表展示)和Ganglia
    //取消slf4j是因为日志级别默认info，无法打印
    //static Slf4jReporter reporter = Slf4jReporter
    static ConsoleReporter reporter = ConsoleReporter
            .forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

    //监控计数器
    //static final Counter items = metricRegistry.counter("items");
    static final Counter items2 = metricRegistry.counter("counter");

    public static void main(String[] args) {
        reporter.start(1, TimeUnit.SECONDS);
        //flow20.doOnNext(x -> items.inc()).subscribe();
        flow20
                .doOnNext(x -> items2.inc())
                .flatMap((l) ->
                   Observable.just(l).delay(100+ l *10,TimeUnit.MILLISECONDS)
                )
                .doOnNext(x -> items2.dec())
                .subscribe();
        sleep(10,TimeUnit.SECONDS);
    }
}
