package com.ccand99.webclient.envtemp.business

import com.ccand99.r2dbc.Customer
import com.ccand99.webclient.util.WECAHT_HTTP_BASE_URL
import com.ccand99.webclient.envtemp.ApiServer
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ApiServer(WECAHT_HTTP_BASE_URL)
interface IServerApi {

    /**
     * consumes = [TEXT_PLAIN_VALUE] 表示服务端返回 默认json
     * produces = [APPLICATION_JSON_VALUE] 表示发出的格式 默认json
     */
    @GetMapping("/eee")
    fun loginWeChat(
        @PathVariable("appid")APP_ID: String?= null,
        @PathVariable("secret")APP_SECRET: String?= null,
        @PathVariable("js_code")code: String?= null,
        @PathVariable("grant_type")grantType: String?=null
    ): Flux<Customer>

    @PostMapping("/unifiedorder", consumes = [MediaType.TEXT_XML_VALUE], produces = [MediaType.TEXT_XML_VALUE])
    fun unifiedOrder(@RequestBody unifiedOrder: Mono<UnifiedOrder>): Mono<UnifiedOrderWxResponse>
}