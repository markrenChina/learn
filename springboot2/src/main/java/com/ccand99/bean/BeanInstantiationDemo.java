package com.ccand99.bean;

import com.ccand99.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean 实例化示例
 */
public class BeanInstantiationDemo {
    public static void main(String[] args) {
        //启动Spring应用上下文
        BeanFactory beanFactory = new
                ClassPathXmlApplicationContext(
                "classpath:/META-INF/special-bean-instantiation-context.xml");
        User user = beanFactory.getBean("user-by-static-method", User.class);
        User user2 = beanFactory.getBean("use-by-instance-method", User.class);
        User user3 = beanFactory.getBean("use-by-factory-bean", User.class);
        System.out.println(user);
        System.out.println(user2);
        System.out.println(user3);
    }
}
