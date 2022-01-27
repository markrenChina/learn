package com.ccand99.features.advisor;

import com.ccand99.overview.EchoService;
import org.springframework.aop.IntroductionInfo;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

import java.lang.reflect.Method;

public class IntroductionAdvisorDemo implements EchoService, Comparable<IntroductionAdvisorDemo> {
    public static void main(String[] args) {
        IntroductionAdvisorDemo introductionAdvisorDemo = new IntroductionAdvisorDemo();
        //使用下面的构造器会是的IntroductionInfo 失效
        //ProxyFactory proxyFactory = new ProxyFactory(introductionAdvisorDemo);
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(introductionAdvisorDemo);
        //添加 IntroductionAdvisorDemo
        proxyFactory.addAdvisor(new DefaultIntroductionAdvisor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("before");
            }
        }, new IntroductionInfo() {
            @Override
            public Class<?>[] getInterfaces() {
                //需要扩展可以在数组中放入更多的接口，但是调用的的时候可能会出错。
                return new Class[]{EchoService.class};
            }
        }));

        Object proxy = proxyFactory.getProxy();
        EchoService echoService = (EchoService) proxy;
        System.out.println(echoService.echo("Hello, World"));
        // 都被before 拦截了
        Comparable comparable = (Comparable) proxy;
        comparable.compareTo(null);
    }

    @Override
    public String echo(String message) throws NullPointerException {
        return "echo " + message;
    }

    @Override
    public int compareTo(IntroductionAdvisorDemo o) {
        return 0;
    }
}
