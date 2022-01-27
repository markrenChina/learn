package com.ccand99.features;

import com.ccand99.features.aspect.AspectConfiguration;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AspectJUsingAPIDemo {

    public static void main(String[] args) {
        //通过创建一个HashMap缓存,作为被代理的对象
        Map<String, Object> cache = new HashMap<>();
        //创建Proxy 工厂(Aspectj)
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(cache);
        //增加 Aspect配置类
        proxyFactory.addAspect(AspectConfiguration.class);
        //在埋点织入代码
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                if ("put".equals(method.getName()) && args.length == 2) {
                    System.out.printf("[MethodBeforeAdvice]当前存放是 Key： %s , Value : %s %n", args[0], args[1]);
                }
            }
        });
        //添加AfterReturningAdvice
        proxyFactory.addAdvice(new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                if ("put".equals(method.getName()) && args.length == 2) {
                    System.out.printf("[AfterReturningAdvice]当前存放是 Key： %s , 新的Value : %s 之前关联的Value： %s %n",
                            args[0],
                            args[1],
                            returnValue
                    );
                }
            }
        });

        //cache.put("1","A");
        Map<String, Object> proxy = proxyFactory.getProxy();
        proxy.put("1", "A");
        proxy.put("1", "B");
        System.out.println(cache.get("1"));
    }
}
