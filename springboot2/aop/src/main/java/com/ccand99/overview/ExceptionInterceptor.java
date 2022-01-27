package com.ccand99.overview;

import java.lang.reflect.Method;

/**
 * 异常拦截器
 */
public interface ExceptionInterceptor {

    /**
     * @param proxy
     * @param method
     * @param args
     * @param throwable 异常信息
     */
    void handler(Object proxy, Method method, Object[] args, Throwable throwable);
}
