package com.ccand99.features;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect // 声明为Aspect切面
@Configuration
@EnableAspectJAutoProxy //激活Aspect 注解自动代理
public class AspectJDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AspectJDemo.class);
        context.refresh();
        AspectJDemo aspectJDemo = context.getBean(AspectJDemo.class);

        context.close();
    }
}
