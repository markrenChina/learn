package com.ccand99.other;

import com.ccand99.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * {@link ObjectFactory} 延迟依赖查找示例
 *
 * @see ObjectFactory
 * @see ObjectProvider
 */
public class ObjectFactoryLazyLookupDemo {

    /**
     * 相当于一个代理
     */
    @Autowired
    private ObjectFactory<User> userObjectFactory;
    @Autowired
    private ObjectProvider<User> userObjectProvider;

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.register(ObjectFactoryLazyLookupDemo.class);
        context.refresh();
        var demo = context.getBean(ObjectFactoryLazyLookupDemo.class);
        System.out.println(demo.userObjectFactory == demo.userObjectProvider);
        System.out.println(demo.userObjectFactory.getClass() == demo.userObjectProvider.getClass());
        //Lazy 状态下，这条语句才会初始化这个bean 等同于依赖查找初始化
        System.out.println("user = " + demo.userObjectFactory.getObject());
        System.out.println("user = " + demo.userObjectFactory.getObject());
        context.close();

    }

    @Bean
    @Lazy
    public static User user() {
        System.out.println("create user");
        return new User() {{
            setId("markrenChina");
        }};
    }
}
