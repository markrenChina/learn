package com.ccand99.metadata;

import com.ccand99.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * 基于注解 的 YAML 外部化配置
 */
@PropertySource(
        name = "yamlPropertySource",
        value = "classpath:/META-INF/user.yml",
        factory = YamlPropertySourceFactory.class)
public class AnnotatedYamlPropertySourceDemo {

    /**
     * @param id
     * @return
     */
    @Bean
    public User configuredUser(@Value("${user.id}") String id){
        System.out.println("configuredUser");
        System.out.println("id = " + id);
        return new User(id);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册当前类为Configuration Class
        context.register(AnnotatedYamlPropertySourceDemo.class);
        //启动Spring 应用上下文
        context.refresh();
        //beanName 和 bean 映射
        Map<String, User> userMap = context.getBeansOfType(User.class);
        userMap.forEach((key, value) -> System.out.printf("User Bean name : %s, content: %s \n",key,value));
        //var user = context.getBean("user", User.class);
        //System.out.println(yamlMap);
        context.close();
    }
}
