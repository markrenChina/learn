package com.ccand99.overview;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * AOP 目标过滤示例
 */
public class TargetFilterDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        String targetClassName = "com.ccand99.overview.EchoService";
        //获取当前线程的 ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //获取目标类
        Class<?> targetClass = classLoader.loadClass(targetClassName);
        //方法定义： String echo(String message)
        //Spring 反射工具类
        Method echo = ReflectionUtils.findMethod(targetClass, "echo", String.class);
        System.out.println(echo);

        //查找方法 throw 类型为NullPointerException
        ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                System.out.printf("这是一个仅抛出NullPointerException的方法：%n" + method);
            }
        }, new ReflectionUtils.MethodFilter() {
            @Override
            public boolean matches(Method method) {
                //参数判断
                //Class<?>[] parameterTypes = method.getParameterTypes();
                //抛出异常判断
                Class<?>[] exceptionTypes = method.getExceptionTypes();
                return exceptionTypes.length == 1 && NullPointerException.class.equals(exceptionTypes[0]);
            }
        });
    }
}
