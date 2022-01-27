package com.ccand99.features;

import com.ccand99.features.interceptor.EchoServiceMethodInterceptor;
import com.ccand99.features.pointcut.EchoServiceEchoMethodPointcut;
import com.ccand99.features.pointcut.EchoServicePointcut;
import com.ccand99.overview.DefaultEchoService;
import com.ccand99.overview.EchoService;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class RawPointAPIDemo {

    public static void main(String[] args) {
        EchoServicePointcut pointcut1 = new EchoServicePointcut("echo", EchoService.class);
        EchoServiceEchoMethodPointcut pointcut2 = new EchoServiceEchoMethodPointcut();

        ComposablePointcut composablePointcut = new ComposablePointcut(pointcut2);
        composablePointcut.intersection(pointcut1.getClassFilter());
        composablePointcut.intersection(pointcut1.getMethodMatcher());
        //将Pointcut 适配成 Advisor
        //DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut,new EchoServiceMethodInterceptor());
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(composablePointcut, new EchoServiceMethodInterceptor());

        DefaultEchoService defaultEchoService = new DefaultEchoService();
        ProxyFactory proxyFactory = new ProxyFactory(defaultEchoService);
        proxyFactory.addAdvisor(advisor);
        //获取代理对象
        EchoService echoService = (EchoService) proxyFactory.getProxy();
        echoService.echo("hello world！");
        System.out.println(echoService);
    }
}
