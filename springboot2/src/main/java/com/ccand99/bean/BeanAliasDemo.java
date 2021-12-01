package com.ccand99.bean;

import com.ccand99.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAliasDemo {

    public static void main(String[] args) {
        //启动Spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definitions-context.xml");
        //通过别名获取bean
        User aliasUser = beanFactory.getBean("alias-user",User.class);
        User user = beanFactory.getBean("user",User.class);
        System.out.println("aliasUser == user is"+ (aliasUser == user));
    }

}
