package com.ccand99.lifecycle;

import org.springframework.context.Lifecycle;
import org.springframework.context.support.GenericApplicationContext;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * 自定义 {@link Lifecycle} Bean 示例
 *
 * @see Lifecycle
 */
public class LifecycleDemo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBeanDefinition(
                "myLifecycle",
                rootBeanDefinition(MyLifecycle.class).getBeanDefinition());
        context.refresh();

        //启动Spring 应用上下文
        context.start();

        //停止Spring 应用上下文
        context.stop();

        context.close();

    }
}
