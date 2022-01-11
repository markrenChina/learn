package com.ccand99.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 自定义 Spring 事件示例
 */
public class CustomizedSpringEventDemo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        //1. 添加自定义Spring 事件监听器
        context.addApplicationListener(new MySpringEventListener());
        //2. 启动 Spring 应用上下文
        context.refresh();
        //3. 发布自定义 Spring事件
        context.publishEvent(new MySpringEvent("CustomizedSpringEventDemo"));

        context.close();
    }

    static class MySpringEvent extends ApplicationEvent {

        public MySpringEvent(Object source) {
            super(source);
            //System.out.printf("线程 ： %s ，内容 %s\n",Thread.currentThread().getName(),source);
        }
    }

    static class MySpringEventListener implements ApplicationListener<MySpringEvent> {
        @Override
        public void onApplicationEvent(MySpringEvent event) {
            System.out.printf("线程 ： %s ，内容 %s\n", Thread.currentThread().getName(), event);
            //System.out.println(event);
        }
    }
}
