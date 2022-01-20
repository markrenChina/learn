package com.ccand99.other;

import com.ccand99.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * {@link Bean} 缓存示例
 *
 * @see ObjectFactory
 * @see ObjectProvider
 */
public class BeanCachingDemo {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.register(BeanCachingDemo.class);
        context.refresh();
        var app = context.getBean(BeanCachingDemo.class);
        for (int i = 0; i < 10; i++) {
            //Singleton Scope bean 是存在缓存的
            System.out.println(app == context.getBean(BeanCachingDemo.class));
        }
        User user = context.getBean(User.class);
        for (int i = 0; i < 10; i++) {
            //prototype Scope bean 不存在缓存
            System.out.println(user == context.getBean(User.class));
        }

        context.close();
    }


    @Bean
    @Scope("prototype")
    public static User user() {
        System.out.println("create user");
        return new User() {{
            setId("markrenChina");
        }};
    }
}
