package com.ccand99.overview;

import java.lang.reflect.Method;

/**
 * 前置拦截器
 */
public interface BeforeInterceptor {

    /**
     * 前置执行
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    Object before(Object proxy, Method method, Object[] args);
}
