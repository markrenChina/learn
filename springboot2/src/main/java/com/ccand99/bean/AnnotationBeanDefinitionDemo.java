package com.ccand99.bean;

import com.ccand99.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 注解 BeanDefiniton 示例
 */
@Import(AnnotationBeanDefinitionDemo.Config.class) //3.通过@Import 来进行导入
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 Cofiguration Class （配置类）
        //applicationContext.register(Config.class);
        applicationContext.register(AnnotationBeanDefinitionDemo.class);
        //通过 BeanDefinition 注册API实现
        //命名
        registerUserBeanDefinition(applicationContext,"mark");
        //非命名
        registerUserBeanDefinition(applicationContext);
        //启动Spring应用上下文
        applicationContext.refresh();
        //检查是否会重复注入
        Map<String,Config> configBeans = applicationContext.getBeansOfType(Config.class);
        System.out.println("All configBean :" + configBeans);
        Map<String,User> users = applicationContext.getBeansOfType(User.class);
        System.out.println("All userBean :" + users);

        //显示地关闭spring应用上下文
        applicationContext.close();
    }

    //通过api的方式 ,命名bean的注册方式
    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id","mark")
                .addPropertyValue("age",1);
        //判断beanName是否存在
        if(StringUtils.hasText(beanName)){
            //注册 BeanDefinition
            registry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());
        }else {
            //非命名的方式注册
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(),registry);
        }

    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry){
        registerUserBeanDefinition(registry,null);
    }

    @Component //2.通过@Component 方式
    public static class Config{
        /**
         * 1. 通过@Bean 方式定义
         * 函数需要驱动
         */
        @Bean(name = {"user","alis-user"}) //1.
        public User user() {
            return new User("annotation");
        }
    }
}
