package com.ccand99.overview;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.Instant;

/**
 * JDK 动态代理
 */
public class JdkDynamicProxyDemo {

    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{EchoService.class}, new InvocationHandler() {
            EchoService echoService = new DefaultEchoService();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (EchoService.class.isAssignableFrom(method.getDeclaringClass())) {
                    Instant startTime = Instant.now();
                    //Object res = method.invoke(proxy,args[0]);
                    String res = echoService.echo((String) args[0]);
                    Duration costTime = Duration.between(startTime, Instant.now());
                    System.out.println("执行时间：" + costTime.getNano() + " Nano");
                    return res;
                }
                return null;
            }
        });
        EchoService echoService = (EchoService) proxy;
        echoService.echo("Hello,World");
    }
}
