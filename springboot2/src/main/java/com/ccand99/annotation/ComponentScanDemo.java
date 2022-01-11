package com.ccand99.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * {@link Component} 扫描示例
 *
 * @see Component
 * @see ComponentScan
 */
@ComponentScan
public class ComponentScanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ComponentScanDemo.class);
        context.refresh();
        //依赖查找TestClass Bean
        TestClass testClass = context.getBean(TestClass.class);
        System.out.println(testClass);
        context.close();
    }
}
