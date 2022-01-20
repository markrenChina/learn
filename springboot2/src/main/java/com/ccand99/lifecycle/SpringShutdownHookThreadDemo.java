package com.ccand99.lifecycle;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.io.IOException;

/**
 * Spring Shutdown Hook 线程示例
 */
public class SpringShutdownHookThreadDemo {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();
        context.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                System.out.printf("线程 %s ContextClosedEvent%n", Thread.currentThread().getName());
            }
        });

        //注册 Shutdown Hook
        context.registerShutdownHook();
        System.out.println("按任意键继续并且关闭Spring 应用上下文");
        System.in.read();
        //同步 关闭
        context.close();
    }
}
