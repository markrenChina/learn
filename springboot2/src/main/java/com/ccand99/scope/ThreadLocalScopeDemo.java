package com.ccand99.scope;

import com.ccand99.domain.User;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 自定义Scope 示例
 */
public class ThreadLocalScopeDemo {

    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public User user(){
        return createUser();
    }

    private static User createUser() {
        User user = new User();
        user.setId(""+System.nanoTime());
        return user;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ThreadLocalScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            //注册自定义的scope
            beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME,new ThreadLocalScope());
        });

        applicationContext.refresh();
        scopeBeansByLookup(applicationContext);

        applicationContext.close();
    }

    private static void scopeBeansByLookup(AnnotationConfigApplicationContext applicationContext){
        Stream.of(1,2,3).forEach((i) ->{
            new Thread(() -> {
                User user = applicationContext.getBean("user",User.class);
                System.out.println("user = "+ user);
            })
                    .start();
        });
    }
}
