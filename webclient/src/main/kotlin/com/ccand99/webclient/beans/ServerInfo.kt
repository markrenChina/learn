package com.zhipuchina.springcloud.commonapi.webclient.beans

/**
 *  服务器信息类
 *  这个类对应ApiServer注解
 *  如果ApiServer注解属性扩展这个类也应该相应扩展
 */

data class ServerInfo(
    val url:String,
    val isLoadBalanced: Boolean = false
)
