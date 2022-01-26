package com.zhipuchina.springcloud.commonapi.webclient.annotation

import com.zhipuchina.springcloud.commonapi.webclient.HttpClientImportSelector
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(HttpClientImportSelector::class)
annotation class EnableProxyHttpClient {
    //todo 选择是否装配负载均衡
}