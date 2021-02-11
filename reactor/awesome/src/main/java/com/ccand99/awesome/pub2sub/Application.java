package com.ccand99.awesome.pub2sub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

//开启异步执行功能
@EnableAsync
@SpringBootApplication
public class Application implements AsyncConfigurer {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(100);
        //如果不配置队列容量5，线程池就无法增长，因为程序转而使用SynchronousQueue
        executor.setQueueCapacity(5);
        log.debug("配置线程池完成");
        executor.initialize();

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        //配置异步处理异常程序
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
