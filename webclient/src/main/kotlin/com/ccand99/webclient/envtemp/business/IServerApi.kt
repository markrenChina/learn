package com.ccand99.webclient.envtemp.business

import com.ccand99.r2dbc.Customer
import com.ccand99.webclient.util.WECAHT_HTTP_BASE_URL
import com.ccand99.webclient.envtemp.ApiServer

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactor.core.publisher.Flux

@ApiServer(WECAHT_HTTP_BASE_URL)
interface IServerApi {

    @GetMapping("/eee")
    fun loginWeChat(
        @PathVariable("appid")APP_ID: String?= null,
        @PathVariable("secret")APP_SECRET: String?= null,
        @PathVariable("js_code")code: String?= null,
        @PathVariable("grant_type")grantType: String?=null
    ): Flux<Customer>
}