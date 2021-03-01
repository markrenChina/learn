package com.ccand99.webclient.envtemp

import com.ccand99.webclient.envtemp.beans.MethodInfo
import com.ccand99.webclient.envtemp.beans.ServerInfo
import com.zhipuchina.loginserverc.envtemp.interfaces.ProxyCreator
import com.zhipuchina.loginserverc.envtemp.interfaces.RestHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.reflect.*

/**
 *  使用jdk动态代理实现代理类
 */

class ProxyCreatorImpl : ProxyCreator {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun createProxy(type: Class<*>): Any {
        logger.info("createProxy: $type")
        //根据接口得到api服务器信息
        val serverInfo = extractServerInfo(type)
        logger.info("serverInfo: $serverInfo")
        //给每一个代理类一个实现
        val handler: RestHandler = WebClientRestHandler(serverInfo)

        return Proxy.newProxyInstance(this.javaClass.classLoader, arrayOf(type), object : InvocationHandler {
            override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
                //根据方法和参数得到调用信息
                val methodInfo = extractMethodInfo(method, args)
                logger.info("methodInfo: $methodInfo")
                //调用rest
                return handler.invokeRest(methodInfo)
            }

            /**
             * 根据方法定义的调用参数得到调用的相关信息
             */
            private fun extractMethodInfo(method: Method, args: Array<out Any>?): MethodInfo {
                val methodInfo = MethodInfo()
                extractUriAndMethod(method, methodInfo)
                extractReturnInfo(method, methodInfo)
                if (args == null) return methodInfo
                extractRequestParamAndBody(method, args, methodInfo)
                return methodInfo
            }

            private fun extractReturnInfo(method: Method, methodInfo: MethodInfo) {
                // 返回flux还是mono
                methodInfo.isReturnFlux = method.returnType.isAssignableFrom(Flux::class.java)
                //logger.info("isFlux ${methodInfo.isReturnFlux}")
                // 得到返回对象的实际类型
                methodInfo.returnElementType = extractElementType(method.genericReturnType)
            }

            /**
             * 得到泛型类型的实际类型
             */
            private fun extractElementType(genericReturnType: Type): Class<*> {
                val actualTypeArguments = (genericReturnType as ParameterizedType).actualTypeArguments
                return (actualTypeArguments[0] as Class<*>)
                //return (genericReturnType as ParameterizedType).actualTypeArguments[0] as Class<*>
            }


            private fun extractRequestParamAndBody(
                method: Method,
                args: Array<out Any>,
                methodInfo: MethodInfo
            ) {
                //得到调用的参数和body
                val parameters = method.parameters
                val params = LinkedMultiValueMap<String, String>()
                for (index in parameters.indices) {
                    //是否带@PathVariable
                    val aPath = parameters[index].getAnnotation(PathVariable::class.java)
                    aPath?.let {
                        params[it.value] = args[index].toString()
                    }
                    //是否带了requestBody
                    val aBody = parameters[index].getAnnotation(RequestBody::class.java)
                    aBody?.let {
                        methodInfo.body = args[index] as Mono<*>
                        //请求对象的实际类型
                        methodInfo.bodyElementType = extractElementType(parameters[index].parameterizedType)
                    }
                }
                methodInfo.params = params
            }

            private fun extractUriAndMethod(
                method: Method,
                methodInfo: MethodInfo
            ) {
                val annotations = method.annotations
                for (e in annotations) {
                    when (e) {
                        is GetMapping -> {
                            methodInfo.uri = e.value[0]
                            methodInfo.method = HttpMethod.GET
                            methodInfo.returnContextType = e.consumes
                            methodInfo.sendContentType = e.produces
                        }
                        is PostMapping -> {
                            methodInfo.uri = e.value[0]
                            methodInfo.method = HttpMethod.POST
                            methodInfo.returnContextType = e.consumes
                            methodInfo.sendContentType = e.produces
                        }
                        is DeleteMapping -> {
                            methodInfo.uri = e.value[0]
                            methodInfo.method = HttpMethod.DELETE
                            methodInfo.returnContextType = e.consumes
                            methodInfo.sendContentType = e.produces
                        }
                        is PutMapping -> {
                            methodInfo.uri = e.value[0]
                            methodInfo.method = HttpMethod.PUT
                            methodInfo.returnContextType = e.consumes
                            methodInfo.sendContentType = e.produces
                        }
                    }
                }
            }
        })
    }

    /**
     * 提取服务器信息
     * 返回一个对象而不是直接返回url。是为了以后扩展
     */
    private fun extractServerInfo(type: Class<*>): ServerInfo {
        return ServerInfo(type.getAnnotation(ApiServer::class.java).value)
    }


}