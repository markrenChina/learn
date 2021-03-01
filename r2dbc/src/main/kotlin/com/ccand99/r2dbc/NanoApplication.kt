package com.ccand99.r2dbc


import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter

import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.netty.DisposableChannel
import reactor.netty.http.server.HttpServer

class NanoApplication {


}

fun main() {
    val httpHandler = RouterFunctions.toHttpHandler(routes())
    val reactorHttpHandler = ReactorHttpHandlerAdapter(httpHandler)

    HttpServer.create()
        .port(10085)
        .handle(reactorHttpHandler)
        .bind()
        .flatMap(DisposableChannel::onDispose)
        .block()
}

fun routes():RouterFunction<ServerResponse> =
    route(POST("/unifiedorder"),{
        it.bodyToMono(String::class.java).log().flatMap { res->
            println(res)
            ServerResponse.ok().build()}
    })