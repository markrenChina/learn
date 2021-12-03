package com.ccand99.lookup;

import com.ccand99.bean.AnnotationBeanDefinitionDemo;
import com.ccand99.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * 通过ObjectProvider 进行依赖查找
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 ObjectProviderDemo Class （配置类）
        applicationContext.register(ObjectProviderDemo.class);
        //通过 BeanDefinition 注册API实现
        applicationContext.refresh();
        lookupByObjectProvider(applicationContext);

        //显示地关闭spring应用上下文
        applicationContext.close();
    }

    @Bean
    public String helloWorld(){
        return "Hello World";
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }
}
