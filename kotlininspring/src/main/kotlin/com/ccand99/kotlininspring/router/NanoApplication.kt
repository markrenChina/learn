package com.ccand99.kotlininspring.router


import kotlinx.coroutines.flow.flow
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunctions.route
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
    route(GET("/connection"),{
        it.bodyToMono(String::class.java).log().flatMap { ServerResponse.ok().build()}
    })




