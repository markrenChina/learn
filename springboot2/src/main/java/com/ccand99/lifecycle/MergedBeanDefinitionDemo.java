package com.ccand99.lifecycle;

import com.ccand99.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

public class MergedBeanDefinitionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
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
    }
}
