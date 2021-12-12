package com.ccand99.lifecycle;

import com.ccand99.domain.SuperUser;
import com.ccand99.domain.User;
import com.ccand99.injection.UserHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("superUser", beanName) && SuperUser.class.equals(beanClass)) {
            return new SuperUser("创建被拦截");
        }
        return null;//保持默认
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
            //"user" 对象不允许属性赋值（配置元信息 -> 属性值）
            User user = (User) bean;
            user.setId("postProcessAfterInstantiation");
            return false;
        }

        return true;
    }

    /**
     * 如果postProcessAfterInstantiation 返回false 这里不会被执行 （user）
     * postProcessBeforeInstantiation 会跳过bean的实例化，也不会执行 （superuser）
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
            // propertyValues 包含一个 propertyValue number
            // 如果没有任何属性 pvs 为空
            final MutablePropertyValues propertyValues;
            if (pvs instanceof MutablePropertyValues) {
                propertyValues = (MutablePropertyValues) pvs;
            } else {
                propertyValues = new MutablePropertyValues();
            }
            propertyValues.addPropertyValue("number", "1");
            return propertyValues;
        }
        return null;
    }

    /**
     * 实例初始化之前
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setBeanName("postProcessBeforeInitialization");
            //return userHolder;
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setBeanName("postProcessAfterInitialization");
            //return userHolder;
        }
        return null;
    }
}
