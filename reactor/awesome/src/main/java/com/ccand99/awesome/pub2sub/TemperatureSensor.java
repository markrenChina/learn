package com.ccand99.awesome.pub2sub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TemperatureSensor {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    //ApplicationEventPublisher Spring框架提供可以将事件发布到系统
    private final ApplicationEventPublisher publisher;
    //用随机数，模拟业务场景
    private final Random random = new Random();
    //线程获取随机数，模拟业务获取随机数环境
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public TemperatureSensor(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    //当bean就绪时，就启动整个随机温度序列
    @PostConstruct
    public void startProcessing() {
        log.debug("启动整个随机温度序列");
        this.executor.schedule(this::probe,1, TimeUnit.SECONDS);
    }

    private void probe() {
        double temperature = 16 + random.nextGaussian() * 10 ;
        log.debug("new temperature = "+ temperature);
        publisher.publishEvent(new Temperature(temperature));
        //每生成一次事件为下一个事件生成调度一次随机延迟
        executor.schedule(this::probe,random.nextInt(5000),TimeUnit.MILLISECONDS);
    }
}
