package com.ccand99.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link Value }@Value 注解示例
 *
 * @see Value
 */
public class ValueAnnotationDemo {

    @Value("${user.name}")
    private String userName;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ValueAnnotationDemo.class);
        applicationContext.refresh();

        var app = applicationContext.getBean(ValueAnnotationDemo.class);
        System.out.println(app.userName);
        applicationContext.close();
    }
}
