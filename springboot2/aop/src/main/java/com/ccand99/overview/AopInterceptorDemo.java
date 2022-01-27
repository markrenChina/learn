package com.ccand99.overview;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.Instant;

/**
 * AOP 拦截器示例
 */
public class AopInterceptorDemo {

    public static void main(String[] args) {
        //前置模式
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{EchoService.class}, new InvocationHandler() {
            EchoService echoService = new DefaultEchoService();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (EchoService.class.isAssignableFrom(method.getDeclaringClass())) {
                    //前置拦截器
                    BeforeInterceptor beforeInterceptor = new BeforeInterceptor() {
                        @Override
                        public Object before(Object proxy, Method method, Object[] args) {
                            return Instant.now();
                        }
                    };
                    Instant before = null, after = null;
                    String res = null;
                    try {
                        before = (Instant) beforeInterceptor.before(proxy, method, args);
                        res = echoService.echo((String) args[0]);
                        //后置拦截器
                        AfterInterceptor afterInterceptor = new AfterInterceptor() {
                            @Override
                            public Object after(Object proxy, Method method, Object[] args, Object result) {

                                return Instant.now();
                            }
                        };
                        after = (Instant) afterInterceptor.after(proxy, method, args, res);
                        return res;
                    } catch (Exception e) {
                        //异常拦截器
                        ExceptionInterceptor exceptionInterceptor = new ExceptionInterceptor() {
                            @Override
                            public void handler(Object proxy, Method method, Object[] args, Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        };
                    } finally {
                        if (null != before && null != after) {
                            TimeFinallyInterceptor finallyInterceptor = new TimeFinallyInterceptor(before, after);
                            Duration costTime = (Duration) finallyInterceptor.finalize(proxy, method, args, res);
                            System.out.println("执行时间：" + costTime.getNano() + " Nano");
                        }
                    }
                }
                return null;
            }
        });
        EchoService echoService = (EchoService) proxy;
        String echo = echoService.echo("Hello,World");
        System.out.println(echo);
    }
}

class TimeFinallyInterceptor implements FinallyInterceptor {
    final Instant before, after;

    TimeFinallyInterceptor(Instant before, Instant after) {
        this.before = before;
        this.after = after;
    }

    @Override
    public Object finalize(Object proxy, Method method, Object[] args, Object result) {
        return Duration.between(before, after);
    }
}
