package com.ccand99.features;

import com.ccand99.features.interceptor.EchoServiceMethodInterceptor;
import com.ccand99.overview.DefaultEchoService;
import com.ccand99.overview.EchoService;
import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AdvisedSupportListener;
import org.springframework.aop.framework.ProxyFactory;

/**
 * {@link AdvisedSupportListener}  示例
 */
public class AdvisedSupportListenerDemo {

    public static void main(String[] args) {
        DefaultEchoService defaultEchoService = new DefaultEchoService();
        //注入目标对象（被代理）
        ProxyFactory proxyFactory = new ProxyFactory(defaultEchoService);
        //添加Advice实现
        proxyFactory.addAdvice(new EchoServiceMethodInterceptor());
        proxyFactory.addListener(new AdvisedSupportListener() {
            @Override
            public void activated(AdvisedSupport advised) {
                System.out.println("AOP 配置对象： " + advised +" 已激活");
            }

            @Override
            public void adviceChanged(AdvisedSupport advised) {
                System.out.println("AOP 配置对象： " + advised + " 已变化");
            }
        });
        //获取代理对象 激活事件触发（createAOpProxy()）
        EchoService echoService = (EchoService) proxyFactory.getProxy();
        echoService.echo("hello world！");
        System.out.println(echoService);
        //触发改变
        proxyFactory.addAdvice(new Advice() {});
    }
}
