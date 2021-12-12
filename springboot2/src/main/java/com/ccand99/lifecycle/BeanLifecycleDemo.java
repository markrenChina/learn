package com.ccand99.lifecycle;

import com.ccand99.domain.User;
import com.ccand99.injection.UserHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

public class BeanLifecycleDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //方法一
        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        //方法二，作为bean注册
        //添加MyDestructionAwareBeanPostProcessor 销毁
        beanFactory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor());
        //添加CommonAnnotationBeanPostProcessor 解决 @PostConstruct
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

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
        //显式地执行，SmartInitializingSingleton 接口才会生效
        //通常在Spring Application场景使用 将已注册的BeanDefinition 初始化成Spring bean
        beanFactory.preInstantiateSingletons();
        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println(userHolder);
        //执行销毁 只是在ioc容器销毁，并不代表被gc
        beanFactory.destroyBean("userHolder",userHolder);
    }
}
