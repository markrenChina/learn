package com.ccand99.features;

import com.ccand99.features.interceptor.MyThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.util.concurrent.ThreadLocalRandom;

public class ThrowsAdviceDemo {

    public static void main(String[] args) {
        ThrowsAdviceDemo instance = new ThrowsAdviceDemo();
        ProxyFactory proxyFactory = new ProxyFactory(instance);
        proxyFactory.addAdvice(new MyThrowsAdvice());

        ThrowsAdviceDemo proxy = (ThrowsAdviceDemo) proxyFactory.getProxy();
        proxy.execute();
        proxy.execute();
        proxy.execute();
        proxy.execute();
    }

    public void execute() {
        if (ThreadLocalRandom.current().nextBoolean()) throw new RuntimeException();
        System.out.println("execute ...");
    }
}
