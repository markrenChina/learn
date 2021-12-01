package com.ccand99.ioc;

import com.ccand99.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

public class DependencyInjectionDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-injection-context.xml");
        //来源1 ，自定义的bean
        UserRepository userRepository = beanFactory.getBean("userRepository",UserRepository.class);
        //System.out.println(userRepository.getUsers());

        //依赖注入 有
        System.out.println(userRepository.getBeanFactory()); //DefaultListableBeanFactory 来源2 内建的依赖
        System.out.println(userRepository.getBeanFactory() == beanFactory); //false

        //依赖查找 找不到
        //System.out.println(beanFactory.getBean(BeanFactory.class));

        ObjectFactory<User> userFactory = userRepository.getUserObjectFactory();
        System.out.println(userFactory.getObject());

        //ApplicationContext 注入获取的 == beanFactory
        ObjectFactory<ApplicationContext> applicationContextObjectFactory = userRepository.getObjectFactory();
        System.out.println(applicationContextObjectFactory.getObject() == beanFactory); //true

        //来源3 容器内建 Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("获取Environment 类型的bean" + environment);

        whoIocContainer(userRepository,beanFactory);
    }

    private static void whoIocContainer(UserRepository userRepository,BeanFactory beanFactory){

        //ConfigurableApplicationContext <- ApplicationContext <- BeanFactory
        // 为什么又需要
        // ConfigurableApplicationContext#getBeanFactory()
        //装饰器模式

        //这个表达式为什么不成立
        System.out.println(userRepository.getBeanFactory() == beanFactory);
    }
}
