package com.ccand99.databing;

import com.ccand99.domain.Company;
import com.ccand99.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.DataBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link DataBinder} 示例
 */
public class DataBinderDemo {

    public static void main(String[] args) {
        User user = new User();
        DataBinder binder = new DataBinder(user, "");

        //创建 PropertyValues
        Map<String, Object> source = new HashMap<>();
        source.put("id", "DataBinderDemo");
        // PropertyValues 存在user中不存在的属性
        // 特性一： 忽略位置的属性
        source.put("abc", "123");
        //PropertyValues 存在一个嵌套属性，比如 company.name
        // 特性二： 支持嵌套属性
        source.put("company", new Company()); // 测试2 需要打开
        //source.put("company.name","zhipu");
        PropertyValues propertyValues = new MutablePropertyValues(source);
        // 1. 测试 IgnoreUnknownFields true 默认 -> false
        //binder.setIgnoreUnknownFields(false);
        // 2. 测试自动增加嵌套路径 true 默认 -> false
        //binder.setAutoGrowNestedPaths(false);
        // 3. 测试 IgnoreInvalidFields false 默认 -> true
        //binder.setIgnoreInvalidFields(true);
        // 4. 白名单测试，city 是必须的
        binder.setRequiredFields("city");

        binder.bind(propertyValues);
        System.out.println(user);

        // 测试4 获取绑定结果
        System.out.println(binder.getBindingResult());
    }
}
