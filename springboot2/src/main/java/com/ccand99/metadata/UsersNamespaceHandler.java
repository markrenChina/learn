package com.ccand99.metadata;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * user.xsd {@link org.springframework.beans.factory.xml.NamespaceHandler} 的实现
 */
public class UsersNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        //将 "User" 元素注册对应的 BeanDefinitionParser 实现
        registerBeanDefinitionParser("user",new UserBeanDefinitionParser());
    }
}
