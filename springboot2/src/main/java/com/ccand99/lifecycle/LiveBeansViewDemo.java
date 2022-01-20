package com.ccand99.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.LiveBeansView;

import static org.springframework.context.support.LiveBeansView.MBEAN_DOMAIN_PROPERTY_NAME;

/**
 * {@link LiveBeansView} 示例
 */
public class LiveBeansViewDemo {

    public static void main(String[] args) {
        //添加 LiveBeansView 的ObjectName 的domain
        System.setProperty(MBEAN_DOMAIN_PROPERTY_NAME, "com.ccand99");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(LiveBeansViewDemo.class);
        context.refresh();


        context.close();
    }
}
