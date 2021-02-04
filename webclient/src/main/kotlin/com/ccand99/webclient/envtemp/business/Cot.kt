package com.ccand99.webclient.envtemp.business

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Cot {

    @Autowired lateinit var server: IServerApi

    @GetMapping("test")
    fun test() = server.loginWeChat("abc")

}