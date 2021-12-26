package com.ccand99.metadata;

import com.ccand99.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * 外部化配置示例
 */
@PropertySource("classpath:/META-INF/user.properties")
public class PropertySourceDemo {

    /**
     * @param id
     * @return
     */
    @Bean
    public User user(@Value("${user.id}") String id) {
        System.out.println("configuredUser");
        System.out.println(id);
        return new User(id);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        //扩展Environment 中的 PropertySource
        //添加PropertySource 操作必须在 refresh 方法之前完成
        org.springframework.core.env.PropertySource<Map<String, Object>> propertySource =
                new MapPropertySource(
                        "first-property-source",
                        Map.of("user.id", "first-property")
                );
        context.getEnvironment().getPropertySources().addFirst(propertySource);

        //注册当前类为Configuration Class
        context.register(PropertySourceDemo.class);
        //启动Spring 应用上下文
        context.refresh();
        //beanName 和 bean 映射
        Map<String, User> userMap = context.getBeansOfType(User.class);
        userMap.forEach((key, value) -> System.out.printf("User Bean name : %s, content: %s \n", key, value));

        System.out.println(context.getEnvironment().getPropertySources());
        context.close();
    }
}
