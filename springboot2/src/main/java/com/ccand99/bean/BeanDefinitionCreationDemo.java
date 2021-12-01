package com.ccand99.bean;

import com.ccand99.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        //1.通过 BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("age",34);
        beanDefinitionBuilder.addPropertyValue("id","markren");
        //获取bean实例
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        //BeanDefinition 并非Bean终态，可以自定义修改

        //通过AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        //设置Bean的类型
        genericBeanDefinition.setBeanClass(User.class);
        //通过 MutablePropertyValue 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        /*propertyValues.addPropertyValue("age",1);
        propertyValues.addPropertyValue("id","markren1");*/
        propertyValues.add("age",1).add("id","markren1");
        //通过set MutablePropertyValue 批量操作属性
        genericBeanDefinition.setPropertyValues(propertyValues);

    }
}
