package com.ccand99.resource;

import com.ccand99.domain.User;
import com.ccand99.metadata.AnnotatedSpringIocContainerMetadataConfigurationDemo;
import com.ccand99.resource.util.ResourceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 注入 {@link Resource} 对象示例
 * @see Resource
 * @see Value
 * @see AnnotationConfigApplicationContext
 */
public class InjectingResourceDemo {

    @Value("classpath:/META-INF/resource.properties")
    private Resource resource;

    @Value("classpath:/META-INF/*.properties")
    private Resource[] resources;

    @Value("${user.dir}")
    private String currentProjectRootPath;

    @PostConstruct
    public void init(){
        System.out.println(ResourceUtils.getContent(resource));
        System.out.println(currentProjectRootPath);
        Stream.of(resources).map(ResourceUtils::getContent).forEach(System.out::println);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册当前类为Configuration Class
        context.register(InjectingResourceDemo.class);
        //启动Spring 应用上下文
        context.refresh();
        //关闭 Spring 应用上下文
        context.close();
    }
}
