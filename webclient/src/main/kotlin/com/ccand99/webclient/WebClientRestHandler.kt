package com.zhipuchina.springcloud.commonapi.webclient

import com.zhipuchina.springcloud.commonapi.utils.JacksonUtils
import com.zhipuchina.springcloud.commonapi.webclient.beans.MethodInfo
import com.zhipuchina.springcloud.commonapi.webclient.beans.ServerInfo
import com.zhipuchina.springcloud.commonapi.webclient.interfaces.RestHandler
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

class WebClientRestHandler(
    override val serverInfo: ServerInfo,
    private val lbFunction: LoadBalancedExchangeFilterFunction
) : RestHandler {

    private val loadBalancedClient = WebClient.builder().filter(lbFunction).build()
    private val webClient = WebClient.create()

    /**
     * 处理rest请求
     */
    override fun invokeRest(methodInfo: MethodInfo): Any {
        val client = if (serverInfo.isLoadBalanced) loadBalancedClient else webClient
        //val client = WebClient.create()
        //println("is not LoadBalanced client = $webClient")
        //println("isLoadBalanced client = $loadBalancedClient")
        //println(client)
        //构建返回类型
        val backContentType: Array<MediaType> = if (methodInfo.returnContextType.isEmpty()) {
            arrayOf(MediaType.APPLICATION_JSON)
        } else {
            val array: Array<MediaType> = Array(methodInfo.returnContextType.size) { MediaType.APPLICATION_JSON }
            for (index in methodInfo.returnContextType.indices) {
                array[index] = MediaType(
                    methodInfo.returnContextType[index].split("/")[0],
                    methodInfo.returnContextType[index].split("/")[1].split(";")[0]
                )
            }
            array
        }

        //构建发送类型
        val sendContentType: Array<MediaType> = if (methodInfo.sendContentType.isEmpty()) {
            arrayOf(MediaType.APPLICATION_JSON)
        } else {
            val array: Array<MediaType> = Array(methodInfo.sendContentType.size) { MediaType.APPLICATION_JSON }
            for (index in methodInfo.sendContentType.indices) {
                array[index] = MediaType(
                    methodInfo.sendContentType[index].split("/")[0],
                    methodInfo.sendContentType[index].split("/")[1].split(";")[0]
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
            .contentType(sendContentType[0])
            .accept(*backContentType)
        //发出请求
        val retrieve = methodInfo.body?.let {
            request.body(it, methodInfo.bodyElementType).retrieve()
        } ?: request.retrieve()

        //处理异常
        retrieve.onStatus({ status -> status.value() == 404 }, { Mono.just(RuntimeException("not found")) })

        //处理body
        return if (MediaType.APPLICATION_JSON in backContentType) {
            //todo 修改为通过class.forname去创建
            if (methodInfo.isReturnFlux) {
                retrieve.bodyToFlux(ParameterizedTypeReference.forType(methodInfo.returnElementType))//.map {
                    //transition(methodInfo, it)
                    //Gson().fromJson<>(it,methodInfo.returnElementType)
                //}
            } else {
                // mono 这里转String 是为了解决 content-type text/html 却又返回json格式字符串的
                retrieve.bodyToMono(String::class.java).map {
                    //println(it)
                    transition(methodInfo, it)
                }
            }
        } else {
            if (methodInfo.isReturnFlux) {
                retrieve.bodyToFlux(String::class.java)
            } else {
                retrieve.bodyToMono(String::class.java)
            }
        }
    }

    private fun transition(
        methodInfo: MethodInfo,
        json: String,
    ): Any {
        return JacksonUtils.fromJson(json, JacksonUtils.getJavaType(methodInfo.returnElementType))
    }

/*if (methodInfo.returnCommonType.contains(ResultV1::class.java.typeName)) {
        JacksonUtils.fromResultV1Json(json, methodInfo.returnElementType)
    } else {
        JacksonUtils.fromRespJson(json, methodInfo.returnElementType)
    }*/

}
