package com.ccand99.webclient.envtemp.business

import com.ccand99.webclient.envtemp.ProxyCreatorImpl
import com.zhipuchina.loginserverc.envtemp.interfaces.ProxyCreator
import org.springframework.beans.factory.FactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class bean {
    //testApi
    @Bean
    fun serverApi(proxyCreator: ProxyCreator): FactoryBean<IServerApi?>? {
        return object : FactoryBean<IServerApi?> {
            /**
             * 返回代理对象
             * @return
             * @throws Exception
             */
            @Throws(Exception::class)
            override fun getObject(): IServerApi? {
                assert(this.objectType != null)
                return proxyCreator.createProxy(this.objectType!!) as IServerApi
            }

            override fun getObjectType(): Class<*>? {
                return IServerApi::class.java
            }
        }
    }

    //test 传建jdk代理工具类
    @Bean
    fun proxyCreator(): ProxyCreator? {
        return ProxyCreatorImpl()
    }
}