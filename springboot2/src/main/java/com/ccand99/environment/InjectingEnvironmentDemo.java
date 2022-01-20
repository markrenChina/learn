package com.ccand99.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 注入 {@link Environment} 示例
 *
 * @see Environment
 */
public class InjectingEnvironmentDemo implements EnvironmentAware {

    private Environment environment;

    @Autowired
    private Environment environment2;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(InjectingEnvironmentDemo.class);
        applicationContext.refresh();

        var demo = applicationContext.getBean(InjectingEnvironmentDemo.class);
        System.out.println(demo.environment);
        System.out.println(demo.environment == demo.environment2);
        System.out.println(demo.environment == applicationContext.getEnvironment());

        applicationContext.close();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
