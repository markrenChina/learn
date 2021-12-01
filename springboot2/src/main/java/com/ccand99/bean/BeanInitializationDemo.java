package com.ccand99.bean;

import com.ccand99.domain.factory.IUserFactory;
import com.ccand99.domain.factory.UserFactoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();

        //1.依赖查找
        IUserFactory userFactory = applicationContext.getBean(IUserFactory.class);

        applicationContext.close();
    }

    //2.通过initMethod
    @Bean(initMethod = "initUserFactory")
    public IUserFactory userFactory(){
        return new UserFactoryImpl();
    }
}
