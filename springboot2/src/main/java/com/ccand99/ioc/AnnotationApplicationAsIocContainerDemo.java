package com.ccand99.ioc;

import com.ccand99.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class AnnotationApplicationAsIocContainerDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 作为配置类
        applicationContext.register(AnnotationApplicationAsIocContainerDemo.class);
        //启动应用上下文
        applicationContext.refresh();
        DependencyLookupDemo.lookupCollectionByType(applicationContext);
        //关闭应用上下文
        applicationContext.close();
    }

    /**
     * 通过注解定义一个bean
     */
    @Bean
    public User user() {
        return new User("annotation");
    }
}
