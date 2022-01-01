package com.ccand99.bean;

import com.ccand99.domain.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * JavaBeans 示例
 */
public class JavaBeansDemo {

    public static void main(String[] args) throws IntrospectionException {
        //Object 排除（截止）类
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class, Object.class);
        //属性描述符 PropertyDescriptor
        //所有JAVA 类均继承 Object,class 来自Object#getClass
        Stream.of(beanInfo.getPropertyDescriptors())
                //.map(p -> p.getReadMethod().toString() + p.getWriteMethod().toString())
                .forEach(System.out::println);

        System.out.println();
        //输出 User的Method
        Arrays.stream(beanInfo.getMethodDescriptors()).forEach(System.out::println);
    }
}
