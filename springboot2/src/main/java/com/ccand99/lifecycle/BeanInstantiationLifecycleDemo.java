package com.ccand99.lifecycle;

import com.ccand99.domain.SuperUser;
import com.ccand99.domain.User;
import com.ccand99.injection.UserHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.ObjectUtils;

/**
 * 可以通过代理的方式替换spring原生的类创建
 */
public class BeanInstantiationLifecycleDemo {

    public static void main(String[] args) {
        executeBeanFactory();
        System.out.println("======================================");
        executeApplicationContext();
    }

    private static void executeBeanFactory(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //方法一
        //beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        //方法二，作为bean注册

        //基于 XML 资源 BeanDefinitionReader 实现
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String location = "META-INF/dependency-lookup-context.xml";
        //基于ClassPath加载xml资源
        Resource resource = new ClassPathResource(location);
        //指定字符编码
        EncodedResource encodedResource = new EncodedResource(resource,"UTF-8");
        //加载 properties 资源
        int beanNames = beanDefinitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("beanNames = " + beanNames);
        //通过依赖查找
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);

        User superUser = beanFactory.getBean("superUser", User.class);
        System.out.println(superUser);

        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println(userHolder);
    }

    private static void executeApplicationContext(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        //beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        String location = "META-INF/dependency-lookup-context.xml";
        //基于 XML 资源 BeanDefinitionReader 实现
        applicationContext.setConfigLocation(location);
        applicationContext.refresh();
        //通过依赖查找
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);

        User superUser = applicationContext.getBean("superUser", User.class);
        System.out.println(superUser);

        UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
        System.out.println(userHolder);
        applicationContext.close();
    }

}
