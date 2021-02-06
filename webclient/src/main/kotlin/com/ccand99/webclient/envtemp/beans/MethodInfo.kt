package com.ccand99.webclient.envtemp.beans

import org.springframework.http.HttpMethod
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Mono

/**
 *  方法调用信息类
 */
class MethodInfo{
    lateinit var uri : String
    lateinit var method: HttpMethod
    var params: MultiValueMap<String, String>?= null
    var body: Mono<*>?= null

    /**
     * 请求body 的类型
     */
    lateinit var bodyElementType: Class<*>
    /**
     * 返回对象还是流
     */
    var isEntity = false
    /**
     * 返回是flux还是momo
     */
    var isReturnFlux: Boolean = false

    /**
     *  返回对象的类型
     */
    lateinit var returnElementType: Class<*>

    lateinit var returnContextType: Array<String>
    lateinit var returnElementContextType: Array<String>

    override fun toString(): String {
        return "MethodInfo(uri='$uri', method=$method, params=$params, body=$body)"
    }
}