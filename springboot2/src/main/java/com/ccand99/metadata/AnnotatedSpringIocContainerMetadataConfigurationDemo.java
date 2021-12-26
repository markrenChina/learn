package com.ccand99.metadata;

import com.ccand99.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.Map;

/**
 * 基于 Java 注解 Spring Ioc 容器元信息配置示例
 */
//将当前类作为Configuration Class
@ImportResource("classpath:/META-INF/dependency-lookup-context.xml")
@Import(User.class)
//Java 8+ @Repeatable 支持
@PropertySource("classpath:/META-INF/user.properties")
@PropertySource("classpath:/META-INF/user.properties")
public class AnnotatedSpringIocContainerMetadataConfigurationDemo {

    /**
     * @param id
     * @return
     */
    @Bean
    public User configuredUser(@Value("${user.id}") String id){
        System.out.println("configuredUser");
        return new User(id);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册当前类为Configuration Class
        context.register(AnnotatedSpringIocContainerMetadataConfigurationDemo.class);
        //启动Spring 应用上下文
        context.refresh();
        //beanName 和 bean 映射
        Map<String,User> userMap = context.getBeansOfType(User.class);
        userMap.forEach((key, value) -> System.out.printf("User Bean name : %s, content: %s \n",key,value));

        context.close();
    }
}
