package com.ccand99.ioc;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.stream.Stream;

import com.ccand99.domain.User;

public class BeanInfoDemo {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class,Object.class);
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    //propertyDescriptor 允许添加属性编辑器 PropertyEditor
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    //获取当前属性名称
                    String propertyName = propertyDescriptor.getName();
                    if ("age".equals(propertyName)){
                        //为"age"字段/属性增加 PropertyEditor
                        propertyDescriptor.setPropertyEditorClass(IocUtils.StringToIntegerPropertyEditor.class);
                    }
                });
    }

}
