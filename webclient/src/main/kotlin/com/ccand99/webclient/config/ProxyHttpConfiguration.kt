package com.zhipuchina.springcloud.commonapi.webclient.config

import com.zhipuchina.springcloud.commonapi.webclient.ProxyCreatorImpl
import com.zhipuchina.springcloud.commonapi.webclient.interfaces.ProxyCreator
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import springfox.documentation.spring.web.plugins.Docket

@Component
class ProxyHttpConfiguration {

    //test 传建jdk代理工具类
    @Bean
    @ConditionalOnClass(ReactorLoadBalancerExchangeFilterFunction::class)
    @ConditionalOnMissingBean(ProxyCreator::class)
    fun proxyCreator(lbFunction: LoadBalancedExchangeFilterFunction): ProxyCreator {
        return ProxyCreatorImpl(lbFunction)
    }
}

