package com.zhipuchina.springcloud.commonapi.webclient.interfaces

import com.zhipuchina.springcloud.commonapi.webclient.beans.MethodInfo
import com.zhipuchina.springcloud.commonapi.webclient.beans.ServerInfo


/**
 * rest请求调用handler
 *
 */
interface RestHandler {
    /**
     * 初始化服务器信息
     */
    val serverInfo: ServerInfo

    /**
     * 调用rest请求，返回接口
     */
    fun invokeRest(methodInfo: MethodInfo): Any
}