package com.zhipuchina.loginserverc.envtemp.interfaces

import com.ccand99.webclient.envtemp.beans.MethodInfo
import com.ccand99.webclient.envtemp.beans.ServerInfo

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