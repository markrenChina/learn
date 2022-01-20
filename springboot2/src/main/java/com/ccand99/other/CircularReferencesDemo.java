package com.ccand99.other;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 循环引用（依赖）示例
 */
public class CircularReferencesDemo {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.register(CircularReferencesDemo.class);

        //如果设为false 则抛出异常Is there an unresolvable circular reference? 默认true
        context.setAllowCircularReferences(true);
        context.refresh();
        //var app = context.getBean(CircularReferencesDemo.class);
        System.out.println("Student = " + context.getBean(Student.class));
        System.out.println("ClassRoom = " + context.getBean(ClassRoom.class));
        context.close();
    }

    @Bean
    public static Student student() {
        return new Student() {{
            setId(1L);
            setName("mark");
        }};
    }

    @Bean
    public static ClassRoom classRoom() {
        return new ClassRoom() {{
            setName("110");
        }};
    }
}
