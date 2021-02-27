package com.ccand99.webclient.envtemp

import com.google.gson.Gson
import com.ccand99.webclient.envtemp.beans.MethodInfo
import com.ccand99.webclient.envtemp.beans.ServerInfo
import com.zhipuchina.loginserverc.envtemp.interfaces.RestHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

class WebClientRestHandler(override val serverInfo: ServerInfo) : RestHandler {

    /**
     * 初始化webClient
     */
    private val client = WebClient.create()

    /**
     * 处理rest请求
     */
    override fun invokeRest(methodInfo: MethodInfo): Any {

        //构建返回类型
        val contextType: Array<MediaType> = if (methodInfo.returnContextType.isEmpty()){
            arrayOf(MediaType.APPLICATION_JSON)
        }else{
            val array:Array<MediaType> = Array(methodInfo.returnContextType.size) { MediaType.APPLICATION_JSON }
            for (index in  methodInfo.returnContextType.indices){
                array[index] = MediaType(
                    methodInfo.returnContextType[index].split("/")[0],
                    methodInfo.returnContextType[index].split("/")[1].split(";")[0]
                )
            }
            array
        }

        //构建Uri
        val uri = UriComponentsBuilder
            .fromUriString(serverInfo.url)
            .path(methodInfo.uri)
            .queryParams(methodInfo.params).build().encode().toUri()

        val request = client
            .method(methodInfo.method)
            .uri(uri)
            .accept(*contextType)
        //发出请求
        val retrieve = methodInfo.body?.let {
            //底层还是调用BodyInserters.fromProducer()
            request.body(it,methodInfo.bodyElementType).retrieve()
        }?: request.retrieve() // 如果简单只是处理响应头，使用exchange（）

        //处理异常
        retrieve.onStatus( {status -> status.value() == 404 }, { Mono.just(RuntimeException("not found"))})

        //处理body 这里处理应该根据需求和返回进行更复杂的处理，这里针对text/plain的返回做了json转换
        return if (MediaType.APPLICATION_JSON in contextType){
            if(methodInfo.isReturnFlux){
                retrieve.bodyToFlux(methodInfo.returnElementType)
            }else{
                retrieve.bodyToMono(methodInfo.returnElementType)
            }
        } else {
            if(methodInfo.isReturnFlux){
                retrieve.bodyToFlux(String::class.java).map { Gson().fromJson(it,methodInfo.returnElementType) }
            }else{
                retrieve.bodyToMono(String::class.java).map { Gson().fromJson(it,methodInfo.returnElementType) }
            }
        }
    }
}