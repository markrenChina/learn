package com.ccand99.overview;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

/**
 * CGLIB 动态代理示例
 */
public class CglibDynamicProxyDemo {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        // 指定 super class(组合到cglib提升类中)
        Class<?> superClass = DefaultEchoService.class;
        enhancer.setSuperclass(superClass);
        //指定拦截接口
        enhancer.setInterfaces(new Class[]{EchoService.class});

        //设置方法拦截
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object source, Method method, Object[] objects,
                                    MethodProxy methodProxy) throws Throwable {
                Instant startTime = Instant.now();
                //Source -> CGLIB 子类
                //需要 DefaultEchoService 父类
                //Object res = method.invoke(source,objects);
                Object res = methodProxy.invokeSuper(source, objects);
                Duration costTime = Duration.between(startTime, Instant.now());
                System.out.println("cglib 执行时间：" + costTime.getNano() + " Nano");
                return res;
            }
        });

        //生成代理对象
        EchoService echoService = (EchoService) enhancer.create();
        System.out.println(echoService.echo("hello world"));
    }
}
