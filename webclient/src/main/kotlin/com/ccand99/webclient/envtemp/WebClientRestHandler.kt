package com.ccand99.webclient.envtemp

import com.ccand99.webclient.envtemp.beans.MethodInfo
import com.ccand99.webclient.envtemp.beans.ServerInfo
import com.zhipuchina.loginserverc.envtemp.interfaces.RestHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class WebClientRestHandler(override val serverInfo: ServerInfo) : RestHandler {

    /**
     * 初始化webClient
     */
    private val client = WebClient.create(serverInfo.url)

    /**
     * 处理rest请求
     */
    override fun invokeRest(methodInfo: MethodInfo): Any {
        val request = client
            .method(methodInfo.method)
            .uri(methodInfo.uri,methodInfo.params)
            .accept(MediaType.APPLICATION_JSON)
        //发出请求
        val retrieve = methodInfo.body?.let {
            request.body(it,methodInfo.bodyElementType).retrieve()
        }?: request.retrieve()

        //处理异常
        retrieve.onStatus( {status -> status.value() == 404 }, { Mono.just(RuntimeException("not found"))})

        //处理body
        return if (methodInfo.isReturnFlux){
            retrieve.bodyToFlux(methodInfo.returnElementType)
        }else{
            retrieve.bodyToMono(methodInfo.returnElementType)
        }
    }
}