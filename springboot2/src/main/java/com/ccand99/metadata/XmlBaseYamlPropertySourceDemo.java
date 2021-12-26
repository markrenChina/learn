package com.ccand99.metadata;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * 基于XML 资源 的 YAML 外部化配置
 */
public class XmlBaseYamlPropertySourceDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //XML 配置文件ClassPath 路径
        String location = "classpath:/META-INF/yaml-property-source-context.xml";
        //加载配置
        int beanDefinitionsCount = reader.loadBeanDefinitions(location);
        System.out.println("Bean 定义的加载的数量： " +beanDefinitionsCount);
        //获取Map 的yaml 对象
        var yamlMap = beanFactory.getBean("yamlMap", Map.class);
        System.out.println(yamlMap);
    }
}
