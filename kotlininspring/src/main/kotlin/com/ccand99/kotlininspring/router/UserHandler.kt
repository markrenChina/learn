package com.ccand99.kotlininspring.router

import kotlinx.coroutines.flow.Flow
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.server.*

/**
 * @author markrenChina
 */
val userHandler = beans {
    bean<UserHandler>()
}

class UserHandler(builder: WebClient.Builder) {

    private val client = builder.baseUrl("...").build()

    suspend fun listView(request: ServerRequest): ServerResponse =
        ServerResponse.ok().renderAndAwait("users", mapOf("users" to
                client.get().uri("...").awaitExchange().awaitBody<String>()))

    suspend fun listApi(request: ServerRequest): ServerResponse =
        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(
            client.get().uri("...").awaitExchange().awaitBody<Flow<String>>())
}

fun funRouter(userHandler: UserHandler) = router {

}

