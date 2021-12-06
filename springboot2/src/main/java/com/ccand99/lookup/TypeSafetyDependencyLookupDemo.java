package com.ccand99.lookup;

import com.ccand99.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TypeSafetyDependencyLookupDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 TypeSafetyDependencyLookupDemo Class （配置类）
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        //通过 BeanDefinition 注册API实现
        applicationContext.refresh();

        //演示 BeanFactory#getBean 方法的安全性
        displayBeanFactoryGetBean(applicationContext);
        //演示 ObjectFactory#getObject 方法的安全性
        displayObjectFactoryGetObject(applicationContext);
        //演示 ObjectFactory#getIfAvailable 方法的安全性
        displayObjectProviderIfAvailable(applicationContext);

        //演示 ListableBeanFactory#getBeansOfType方法的安全性
        displayListableBeanFactoryGetBeansOfType(applicationContext);
        //演示 ObjectProviderStream方法的安全性
        displayObjectProviderStreamOps(applicationContext);

        //显示地关闭spring应用上下文
        applicationContext.close();
    }

    private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderStreamOps",()->userObjectProvider.forEach(System.out::println));
    }

    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory beanFactory) {
        printBeansException("displayListableBeanFactoryGetBeansOfType",()-> beanFactory.getBeansOfType(User.class));
    }

    private static void displayObjectProviderIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        //ObjectProvider is ObjectFactory
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderIfAvailable", userObjectProvider::getIfAvailable);
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        //ObjectProvider is ObjectFactory
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectFactoryGetObject", userObjectProvider::getObject);
    }

    private static void printBeansException(String source,Runnable runnable){
        System.err.println("source from "+ source);
        try {
            runnable.run();
        }catch (BeansException exception){
            exception.printStackTrace();
        }
    }

    public static void displayBeanFactoryGetBean(BeanFactory beanFactory){
        printBeansException("displayBeanFactoryGetBean",()->beanFactory.getBean(User.class));
    }
}
