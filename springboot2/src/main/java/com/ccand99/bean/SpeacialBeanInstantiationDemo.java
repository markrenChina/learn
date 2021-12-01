package com.ccand99.bean;

import com.ccand99.domain.User;
import com.ccand99.domain.factory.IUserFactory;
import com.ccand99.domain.factory.UserFactoryImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ServiceLoader;

/**
 * bean 实例化示例
 */
public class SpeacialBeanInstantiationDemo {
    public static void main(String[] args) {
        //启动Spring应用上下文
        ApplicationContext applicationContext = new
                ClassPathXmlApplicationContext(
                "classpath:/META-INF/special-bean-instantiation-context.xml");
        //demoServiceLoader();
        ServiceLoader<IUserFactory> serviceLoader = applicationContext.getBean("userFactoryServiceLoader",ServiceLoader.class);
        displayServiceLoader(serviceLoader);

        //通过AutowireCapableBeanFactory 创建UserFactory 对象
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        IUserFactory userFactory = beanFactory.createBean(UserFactoryImpl.class);
        System.out.println(userFactory.createUser());
    }

    public static void demoServiceLoader(){
        ServiceLoader<IUserFactory> userFactories = ServiceLoader.load(IUserFactory.class,Thread.currentThread().getContextClassLoader());
        displayServiceLoader(userFactories);
    }

    public static void displayServiceLoader(ServiceLoader<IUserFactory> serviceLoader) {
        serviceLoader.forEach(factory -> {
            System.out.println(factory.createUser());
        });
    }
}
