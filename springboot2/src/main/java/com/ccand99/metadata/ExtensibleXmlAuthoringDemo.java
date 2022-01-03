package com.ccand99.metadata;

import com.ccand99.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Spring XML 元素扩展示例
 */
public class ExtensibleXmlAuthoringDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //XML 配置文件ClassPath 路径
        String location = "classpath:/META-INF/user-context.xml";
        //加载配置
        int beanDefinitionsCount = reader.loadBeanDefinitions(location);
        System.out.println("Bean 定义的加载的数量： " +beanDefinitionsCount);

        User user = beanFactory.getBean(User.class);
        System.out.println(user);
    }
}
