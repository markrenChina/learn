package com.ccand99.ioc;

import com.ccand99.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class DependencyLookupDemo {

    public static void main(String[] args) {
        //配置xml启动文件
        //配置spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
        lookupInRealTime(beanFactory);
        lookupInLazy(beanFactory);
        lookupByType(beanFactory);
        lookupCollectionByType(beanFactory);
        lookupByAnnotationType(beanFactory);
    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String,User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("按注解查找 "+ users);
        }
    }

    public static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String,User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("按类型查找集合 "+ users);
        }
    }

    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("type " + user);
    }

    /**
     * 延时查找
     * @param beanFactory
     */
    private static void lookupInLazy(BeanFactory beanFactory) {
        //objectBean 不查找不会生成bean 懒加载
        //FactoryBean 不查找也会生成bean
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("lazy "+user);
    }

    /**
     * 实时查找
     * @param beanFactory
     */
    private static void lookupInRealTime(BeanFactory beanFactory){
        User user = (User) beanFactory.getBean("user");
        System.out.println("real "+user);
    }


}
