package com.ccand99.conversion;

import com.ccand99.domain.User;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 自定义 {@link PropertyEditorRegistrar} 实现
 */
@Component // 3.声明为Spring bean
public class CustomizedPropertyEditorRegistrar implements PropertyEditorRegistrar {
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        //1. 通用类型转换

        //2. Java Bean 属性类型转换
        registry.registerCustomEditor(User.class, "context", new StringToPropertiesPropertyEditor());
    }

    @Bean
    public User user() {
        User user = new User();
        user.setId("PropertyEditorRegistrar");
        Properties context = new Properties();
        context.setProperty("id", "Properties");
        user.setContext(context);
        return user;
    }
}
