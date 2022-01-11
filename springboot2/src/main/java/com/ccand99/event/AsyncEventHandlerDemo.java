package com.ccand99.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步事件处理器示例
 */
public class AsyncEventHandlerDemo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        //1. 添加自定义Spring 事件监听器
        context.addApplicationListener(new CustomizedSpringEventDemo.MySpringEventListener());
        //2. 启动 Spring 应用上下文
        context.refresh();  //会初始化 ApplicationEventMulticaster
        //依赖查找 ApplicationEventMulticaster
        ApplicationEventMulticaster applicationEventMulticaster = context.getBean(
                AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME,
                ApplicationEventMulticaster.class);

        //判断当前 applicationEventMulticaster 是否是 SimpleApplicationEventMulticaster
        if (applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {
            SimpleApplicationEventMulticaster simpleApplicationEventMulticaster =
                    (SimpleApplicationEventMulticaster) applicationEventMulticaster;
            // 切换 taskExecutor
            ExecutorService taskExecutor = Executors.newSingleThreadExecutor(
                    new CustomizableThreadFactory("spring-event-thread-pool"));
            simpleApplicationEventMulticaster.setTaskExecutor(taskExecutor);

            // 添加ContextCloseEvent 事件处理
            applicationEventMulticaster.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
                @Override
                public void onApplicationEvent(ContextClosedEvent event) {
                    //防止事件传播
                    if (!taskExecutor.isShutdown()) {
                        taskExecutor.shutdownNow();
                    }
                }
            });

            simpleApplicationEventMulticaster.setErrorHandler(Throwable::printStackTrace);
        }

        context.addApplicationListener(new ApplicationListener<CustomizedSpringEventDemo.MySpringEvent>() {
            @Override
            public void onApplicationEvent(CustomizedSpringEventDemo.MySpringEvent event) {
                throw new RuntimeException("error");
            }
        });
        //3. 发布自定义 Spring事件
        context.publishEvent(new CustomizedSpringEventDemo.MySpringEvent("CustomizedSpringEventDemo"));

        context.close();
    }
}
