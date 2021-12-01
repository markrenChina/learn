package com.ccand99.domain.factory;

import com.ccand99.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * User Bean 的 {@link org.springframework.beans.factory.FactoryBean} 实现
 */
public class UserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return new User("UserFactoryBean");
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
