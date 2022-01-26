package com.zhipuchina.springcloud.commonapi.webclient.interfaces



/**
 * 创建代理类接口
 *
 */
interface ProxyCreator {
    fun <T> createProxy(type: Class<T>): T
}
