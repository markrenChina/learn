package com.zhipuchina.springcloud.commonapi.webclient.utils

import com.zhipuchina.springcloud.commonapi.webclient.interfaces.ProxyCreator
import org.springframework.beans.factory.FactoryBean

fun <T> creatServerApi(proxyCreator: ProxyCreator, serviceClass: Class<T>): FactoryBean<T?> =
    object : FactoryBean<T?> {
        /**
         * 返回代理对象
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        override fun getObject(): T? {
            return proxyCreator.createProxy(this.objectType)
        }

        override fun getObjectType(): Class<T> {
            return serviceClass
        }
}