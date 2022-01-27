package com.ccand99.overview;

import java.lang.reflect.Method;

/**
 * 后置拦截器
 */
public interface AfterInterceptor {

    /**
     * 后置执行
     *
     * @param proxy
     * @param method
     * @param args
     * @param result 被代理对象的返回结果
     */
    Object after(Object proxy, Method method, Object[] args, Object result);
}
