package com.ccand99.conversion;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * String -> Properties {@link java.beans.PropertyEditor}
 */
public class StringToPropertiesPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Properties properties = (Properties) getValue();

        StringBuilder stringBuilder = new StringBuilder();
        properties.entrySet().forEach(e ->
                stringBuilder.append(e.getKey()).append("=").append(e.getValue()).append(System.getProperty("line.separator")));
        return stringBuilder.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        //将String 类型转换成Properties类型
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(text));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        //临时存储Properties对象
        setValue(properties);
        //next 获取临时 对象
    }
}
