package com.ccand99.conversion;

import java.beans.PropertyEditor;

/**
 * {@link java.beans.PropertyEditor} 示例
 */
public class PropertyEditorDemo {

    public static void main(String[] args) {
        //模拟Spring Framework 操作
        String text = "name = markrenChina";
        PropertyEditor propertyEditor = new StringToPropertiesPropertyEditor();
        propertyEditor.setAsText(text);
        System.out.println(propertyEditor.getValue());
        System.out.println(propertyEditor.getAsText());
    }
}
