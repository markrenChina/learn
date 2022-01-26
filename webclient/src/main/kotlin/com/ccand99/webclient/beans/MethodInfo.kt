package com.zhipuchina.springcloud.commonapi.webclient.beans

import org.springframework.http.HttpMethod
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Mono
import java.lang.reflect.Type


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
    //todo 修改为数组的全类名，后面通过反射class.forname()创建一个对象
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
    //com.zhipuchina.springcloud.commonapi.model.ResultV1<com.zhipuchina.protectcommon.pojo.UnitInfo>
    //lateinit var returnCommonType : String
    //todo 修改为数组的全类名，后面通过反射class.forname()创建一个对象
    lateinit var returnElementType: Type

    lateinit var returnContextType: Array<String>
    lateinit var sendContentType: Array<String>

    override fun toString(): String {
        return "MethodInfo(uri='$uri', method=$method, params=$params, body=$body)"
    }
}
