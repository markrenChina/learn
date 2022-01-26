package com.zhipuchina.springcloud.commonapi.webclient

/**
 * api接口标注
 * @author 任家立
 * v1.0.1 修改默认打开负载均衡
 * @since v1.0.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiServer(
    val value: String = "",
    val isLoadBalanced: Boolean = true
)