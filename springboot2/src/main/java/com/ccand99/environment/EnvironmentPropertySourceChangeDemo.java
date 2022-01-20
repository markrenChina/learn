package com.ccand99.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;

/**
 * {@link Environment} 配置属性原变更示例
 *
 * @see Environment
 */
public class EnvironmentPropertySourceChangeDemo {

    @Value("${user.name}") //不具备动态更新能力
    private String userName;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(EnvironmentPropertySourceChangeDemo.class);
        //在spring应用上下文启动前，调整Environment 中的 PropertySource
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 获取 MutablePropertySources对象
        MutablePropertySources propertySources = environment.getPropertySources();
        //动态地插入 propertySource 到 propertySources
        var source = new HashMap<String, Object>() {{
            put("user.name", "markrenChina");
        }};
        var propertySource = new MapPropertySource("1-source", source);
        propertySources.addFirst(propertySource);

        applicationContext.refresh();

        //启动后修改值
        source.put("user.name", "changed");
        var app = applicationContext.getBean(EnvironmentPropertySourceChangeDemo.class);
        System.out.println(app.userName);

        propertySources.stream()
                .map(ps -> ps.getName() + "  " + ps.getProperty("user.name"))
                .forEach(System.out::println);

        applicationContext.close();
    }

}
