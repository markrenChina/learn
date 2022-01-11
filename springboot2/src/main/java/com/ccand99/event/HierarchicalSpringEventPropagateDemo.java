package com.ccand99.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 层次性Spring 事件传播示例
 */
public class HierarchicalSpringEventPropagateDemo {

    public static void main(String[] args) {
        //1. 创建 parent spring 应用上下文
        AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext();
        parent.setId("parent-context");
        //注册 MyListener 到parent Spring 应用上下文
        parent.register(MyListener.class);
        //2. 创建current Spring 应用上下文
        AnnotationConfigApplicationContext current = new AnnotationConfigApplicationContext();
        current.setId("current-context");
        //current -> parent
        current.setParent(parent);
        //注册 MyListener 到current Spring 应用上下文
        current.register(MyListener.class);
        //启动
        parent.refresh();
        current.refresh(); //MyListener 会被触发2次
        //关闭
        parent.close();
        current.close();
    }

    static class MyListener implements ApplicationListener<ContextRefreshedEvent> {

        //用来判断是否被处理
        private static final Set<ApplicationContextEvent> processed = new LinkedHashSet<>();

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            if (processed.add(event)) {
                System.out.printf("监听到 Spring 应用上下文[ ID: %s] 的 ContextRefreshedEvent\n"
                        , event.getApplicationContext().getId());
            }
        }
    }
}
