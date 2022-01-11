package com.ccand99.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * {@link ApplicationListener} 示例
 */
@EnableAsync
public class ApplicationListenerDemo implements ApplicationEventPublisherAware {

    public static void main(String[] args) {
        //GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ApplicationListenerDemo.class);
        //1,a 基于Spring 接口 向 Spring 应用上下文注册事件 基于ConfigurableApplicationListener API 实现
        context.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                println("ApplicationEvent 接收到 Spring 事件： " + event);
            }
        });
        //1,b 基于 ApplicationListener 注册为Spring Bean
        context.register(MyApplicationListener.class);

        //2， 基于Spring 注解 @EventListener

        // 启动 Spring 应用上下文
        context.refresh();

        context.start();
        //关闭 Spring 应用上下文
        context.close();
    }

    private static void println(Object printable) {
        System.out.printf("[线程 %s] : %s \n", Thread.currentThread().getName(), printable);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        applicationEventPublisher.publishEvent(new ApplicationEvent("Hello applicationEventPublisher") {
        });
        applicationEventPublisher.publishEvent("Hello applicationEventPublisher");
    }

    @EventListener
    @Order(1)
    public void onApplicationEvent(ApplicationEvent event) {
        //ApplicationEvent 可以指定子类
        println("@EventListener 1 接收到 Spring 事件： " + event);
    }

    @EventListener
    @Order(2)
    public void onApplicationEvent2(ApplicationEvent event) {
        //ApplicationEvent 可以指定子类
        println("@EventListener 2 接收到 Spring 事件： " + event);
    }

    @EventListener
    @Async
    public void onApplicationEventAsync(ContextRefreshedEvent event) {
        println("@EventListener Async 接收到 Spring 事件： " + event);
    }

    static class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            println("MyApplicationListener - 接收到 Spring 事件： " + event);
        }
    }
}
