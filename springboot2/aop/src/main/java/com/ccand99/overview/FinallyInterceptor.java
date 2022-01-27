package com.ccand99.overview;

import java.lang.reflect.Method;

/**
 * 最终执行拦截器
 */
public interface FinallyInterceptor {


    /**
     * 最终执行
     *
     * @param proxy
     * @param method
     * @param args
     * @param result 被代理对象的返回结果
     */
    Object finalize(Object proxy, Method method, Object[] args, Object result);
}
