package com.ccand99.conversion;

import com.ccand99.domain.User;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring 自定义 {@link java.beans.PropertyEditor} 示例
 */
public class SpringCustomizedPropertyEditorDemo {

    /*public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 Cofiguration Class （配置类）
        //applicationContext.register(Config.class);
        applicationContext.register(CustomizedPropertyEditorRegistrar.class);
        //启动Spring应用上下文
        applicationContext.refresh();
        User user = applicationContext.getBean("user",User.class);
        System.out.println(user);

        //显示地关闭spring应用上下文
        applicationContext.close();
    }*/

    /**
     * AbstractApplicationContext -> "ConversionService" ConversionService Bean
     * -> ConfigurableBeanFactory#setConversionService(ConversionService)
     * -> AbstractAutowireCapableBeanFactory.instantiateBean
     * -> AbstractBeanFactory#getConversionService ->
     * BeanDefinition -> BeanWrapper -> 属性转换（数据来源：PropertyValues）
     * setPropertyValues(PropertyValues) -> TypeConverter#convertIfNecessnary
     * TypeConverterDelegate#convertIfNecessnary -> PropertyEditor or ConversionService
     */

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/conversion-context.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);

        applicationContext.close();
    }
}
