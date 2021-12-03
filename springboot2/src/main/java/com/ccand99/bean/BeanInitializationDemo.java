package com.ccand99.bean;

import com.ccand99.domain.factory.IUserFactory;
import com.ccand99.domain.factory.UserFactoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) throws InterruptedException {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        System.out.println("Spring 上下文已启动");
        //1.依赖查找
        IUserFactory userFactory = applicationContext.getBean(IUserFactory.class);
        System.out.println(userFactory);
        System.out.println("Spring 准备关闭");
        applicationContext.close();
        System.out.println("Spring 上下文关闭");
        Thread.sleep(500);
        System.gc();
        Thread.sleep(500);
    }

    //2.通过initMethod
    @Bean(initMethod = "initUserFactory",destroyMethod = "doDestroy")
    @Lazy
    public IUserFactory userFactory(){
        return new UserFactoryImpl();
    }
}
