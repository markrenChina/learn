package com.ccand99.validation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Spring Bean Validation 整合示例
 *
 * @see Validator
 * @see LocalValidatorFactoryBean
 */
public class SpringBeanValidationDemo {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-validation-context.xml");

//        Validator validator = context.getBean(Validator.class);
//        System.out.println(validator instanceof  LocalValidatorFactoryBean);

        UserProcess userProcess = context.getBean(UserProcess.class);
        userProcess.process(new User());

        context.close();
    }

    @Component
    @Validated
    static class UserProcess{

        public void process(@Valid User user){
            System.out.println(user);
        }
    }

    static class User{
        @NotNull
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
