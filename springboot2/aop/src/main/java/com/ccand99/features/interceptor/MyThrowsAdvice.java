package com.ccand99.features.interceptor;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MyThrowsAdvice implements ThrowsAdvice {
    public void afterThrowing(Exception e) {
        System.out.printf("exceptions : %s %n", e);
    }

    public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
        System.out.printf("Method : %s , args : %s ,target : %s ,exceptions : %s %n",
                method, Arrays.asList(args), target, e);
    }
}
