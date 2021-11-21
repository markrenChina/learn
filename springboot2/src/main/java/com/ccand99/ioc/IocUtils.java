package com.ccand99.ioc;

import org.springframework.beans.factory.BeanFactory;

import java.beans.PropertyEditorSupport;

public class IocUtils {

    static class StringToIntegerPropertyEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }
}
