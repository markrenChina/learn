package com.ccand99.injection;

import com.ccand99.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * user 的 holder类
 */
public class UserHolder implements
        BeanNameAware,
        BeanClassLoaderAware,
        BeanFactoryAware,
        InitializingBean,
SmartInitializingSingleton,
DisposableBean{

    private User user;

    private Integer number;

    public UserHolder() {
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    private ClassLoader classLoader;
    private BeanFactory beanFactory;
    private String beanName;

    /**
     * 依赖于注解驱动
     */
    @PostConstruct
    public void initializing(){
        this.beanName = "@PostConstruct";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.beanName = "InitializingBean";
    }

    /**
     * 自定义初始化方法
     */
    public void init(){
        this.beanName = "init";
    }
    /**
     * 接口注入
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", number=" + number +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.beanName = "afterSingletonsInstantiated";
    }

    public String getBeanName() {
        return beanName;
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }

    /**
     * 自定义销毁方法
     */
    public void doDestory(){
        System.out.println("doDestory");
    }
}
