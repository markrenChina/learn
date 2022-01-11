package com.ccand99.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 注解驱动异步事件处理器示例
 */
@EnableAsync
public class AnnotatedAsyncEventHandlerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //1.
        context.register(AnnotatedAsyncEventHandlerDemo.class);
        //2. 启动 Spring 应用上下文
        context.refresh();
        //3. 发布自定义 Spring事件
        context.publishEvent(new
                CustomizedSpringEventDemo.MySpringEvent("AnnotatedAsyncEventHandlerDemo"));

        context.close();
    }

    @EventListener
    public void onEvent(CustomizedSpringEventDemo.MySpringEvent event) {
        System.out.printf("[线程 ： %s] onEvent方法监听到事件 : %s\n", Thread.currentThread().getName(), event);
    }

    @Async
    @EventListener
    public void onEvent2(CustomizedSpringEventDemo.MySpringEvent event) {
        System.out.printf("[线程 ： %s] onEvent2方法监听到事件 : %s\n", Thread.currentThread().getName(), event);

    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadExecutor(
                new CustomizableThreadFactory("spring-event-thread-pool"));
    }
}
