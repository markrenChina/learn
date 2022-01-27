package com.ccand99.features;

import com.ccand99.features.aspect.AspectConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Pointcut 示例
 */
@EnableAspectJAutoProxy
public class AspectJPointcutDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AspectJPointcutDemo.class, AspectConfiguration.class
                //,AspectConfiguration2.class
        );
        context.refresh();
        AspectJPointcutDemo aspectJDemo = context.getBean(AspectJPointcutDemo.class);
        aspectJDemo.execute();
        context.close();
    }

    public void execute() {
        System.out.println("execute() ...");
    }
}
