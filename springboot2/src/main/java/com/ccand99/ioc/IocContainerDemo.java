package com.ccand99.ioc;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class IocContainerDemo {
    public static void main(String[] args) {
        //创建BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //xml 配置文件的ClassPath路径
        String location = "classpath:/META-INF/bean-injection-context.xml";
        //加载配置
        int beanDefinitionCount = reader.loadBeanDefinitions(location);
        System.out.println(beanDefinitionCount);
    }
}
