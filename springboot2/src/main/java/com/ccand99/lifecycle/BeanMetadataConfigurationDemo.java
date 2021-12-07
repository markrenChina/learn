package com.ccand99.lifecycle;

import com.ccand99.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * Bean 元信息配置示例
 */
public class BeanMetadataConfigurationDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //实例化基于Properties 资源的 beanDefinitionReader
        PropertiesBeanDefinitionReader beanDefinitionReader =
                new PropertiesBeanDefinitionReader(beanFactory);
        String location = "META-INF/user.properties";
        //基于ClassPath加载资源
        Resource resource = new ClassPathResource(location);
        //指定字符编码
        EncodedResource encodedResource = new EncodedResource(resource,"UTF-8");
        //加载 properties 资源
        int beanNames = beanDefinitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("beanNames = " + beanNames);
        //通过依赖查找
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }
}
