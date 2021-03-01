package com.ccand99.webclient.envtemp

import com.google.gson.Gson
import com.ccand99.webclient.envtemp.beans.MethodInfo
import com.ccand99.webclient.envtemp.beans.ServerInfo
import com.zhipuchina.loginserverc.envtemp.interfaces.RestHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.http.codec.xml.Jaxb2XmlEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

class WebClientRestHandler(override val serverInfo: ServerInfo) : RestHandler {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    /**
     * 初始化webClient
     */
    private var client = WebClient.create()

    /**
     * 处理rest请求
     */
    override fun invokeRest(methodInfo: MethodInfo): Any {

        //构建返回类型
        val backContentType: Array<MediaType> = if (methodInfo.returnContextType.isEmpty()){
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

        //构建发送类型
        val sendContentType: Array<MediaType> = if (methodInfo.sendContentType.isEmpty()){
            arrayOf(MediaType.APPLICATION_JSON)
        }else {
            val array:Array<MediaType> = Array(methodInfo.sendContentType.size) { MediaType.APPLICATION_JSON }
            for (index in  methodInfo.sendContentType.indices){
                array[index] = MediaType(
                    methodInfo.sendContentType[index].split("/")[0],
                    methodInfo.sendContentType[index].split("/")[1].split(";")[0]
                )
            }
            array
        }

        //构建交换策略
        if (MediaType.APPLICATION_XML == sendContentType[0] || MediaType.TEXT_XML == sendContentType[0]){
            val strategies = ExchangeStrategies.builder()
                .codecs { configure ->
                    configure.defaultCodecs().jaxb2Decoder(Jaxb2XmlDecoder())
                    configure.defaultCodecs().jaxb2Encoder(Jaxb2XmlEncoder())
                }
                .build()
            client = client.mutate().exchangeStrategies(strategies).build()
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
        }?: request.retrieve()

        //处理异常
        retrieve.onStatus( {status -> status.value() == 404 }, { Mono.just(RuntimeException("not found"))})

        //处理body
        return if (MediaType.APPLICATION_JSON in backContentType){
            //todo 返回的contextType
            if(methodInfo.isReturnFlux){
                retrieve.bodyToFlux(String::class.java).map { Gson().fromJson(it,methodInfo.returnElementType) }
            }else{
                retrieve.bodyToMono(String::class.java).map { Gson().fromJson(it,methodInfo.returnElementType) }
            }
        } else {
            if(methodInfo.isReturnFlux){
                retrieve.bodyToFlux(String::class.java)
            }else{
                retrieve.bodyToMono(String::class.java)
            }
        }
    }
}