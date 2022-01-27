package com.ccand99.features;

import com.ccand99.features.interceptor.EchoServiceMethodInterceptor;
import com.ccand99.overview.DefaultEchoService;
import com.ccand99.overview.EchoService;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.aop.framework.ProxyFactory;

/**
 * ProxyFactory 代理示例
 *
 * @see DefaultAopProxyFactory#createAopProxy(AdvisedSupport)
 */
public class ProxyFactoryDemo {

    public static void main(String[] args) {
        DefaultEchoService defaultEchoService = new DefaultEchoService();
        //注入目标对象（被代理）
        ProxyFactory proxyFactory = new ProxyFactory(defaultEchoService);
        //添加Advice实现
        proxyFactory.addAdvice(new EchoServiceMethodInterceptor());
        //获取代理对象
        EchoService echoService = (EchoService) proxyFactory.getProxy();
        echoService.echo("hello world！");
        System.out.println(echoService);
    }
}
