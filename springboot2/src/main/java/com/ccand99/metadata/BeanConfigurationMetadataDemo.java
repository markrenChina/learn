package com.ccand99.metadata;

import com.ccand99.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.ObjectUtils;

/**
 * Bean 配置元信息示例
 */
public class BeanConfigurationMetadataDemo {

    public static void main(String[] args) {
        //BeanDefinition 的定义（声明）
        //BeanDefinition beanDefinition = new GenericBeanDefinition();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id","addPropertyValue");
        //获取 AbstractBeanDefinition
        AbstractBeanDefinition beanDefinition =  beanDefinitionBuilder.getBeanDefinition();
        //附加属性(不影响Bean populate、initialize)
        beanDefinition.setAttribute("id","mark");
        //当前 BeanDefinition 来自于何方(辅助作用)
        beanDefinition.setSource(BeanConfigurationMetadataDemo.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("user",beanName) && User.class.equals(bean.getClass())){
                    BeanDefinition userBeanDefinition = beanFactory.getBeanDefinition(beanName);
                    // 属性上下文
                    String id = (String) userBeanDefinition.getAttribute("id");
                    //获取来自何方
                    System.out.println(userBeanDefinition.getSource());

                    User user = (User) bean;
                    user.setId(id);
                    System.out.println(id);
                }
                return bean;
            }
        });
        //注册User 的BeanDefinition
        beanFactory.registerBeanDefinition("user",beanDefinition);

        User user = beanFactory.getBean("user",User.class);
        System.out.println(user);
    }
}
