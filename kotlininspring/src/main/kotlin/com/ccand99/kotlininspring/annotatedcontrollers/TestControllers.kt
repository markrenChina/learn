package com.ccand99.kotlininspring.annotatedcontrollers

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.system.measureTimeMillis

@RestController
class TestControllers() {


    @GetMapping("/suspend")
    suspend fun suspendingEndpoint(): String {
        delay(10)
        return "suspend"
    }

    @GetMapping("/flow")
    fun flowEndpoint() = flow {
        delay(10)
        emit("flow")
        delay(2000)
        emit("flow")
    }

   @GetMapping("/deferred")
   suspend fun deferredEndpoint() = withContext(Dispatchers.Default) {
       delay(10)
       "deferred"
   }

    @GetMapping("await")
    suspend fun await() = coroutineScope {
        measureTimeMillis {
            val one: Deferred<Int> = async (start = CoroutineStart.LAZY){
                delay(2000)
                1+1
            }
            val two: Deferred<Int> = async (start = CoroutineStart.LAZY){
                delay(1000)
                2+2
            }
            one.await() + two.await()
        }
    }
/*
    @GetMapping("/sequential")
    suspend fun sequential(): List<Banner> {
        val banner1 = client
            .get()
            .uri("/suspend")
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange()
            .awaitBody<Banner>()
        val banner2 = client
            .get()
            .uri("/suspend")
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange()
            .awaitBody<Banner>()
        return listOf(banner1, banner2)
    }

    @GetMapping("/parallel")
    suspend fun parallel(): List<Banner> = coroutineScope {
        val deferredBanner1: Deferred<Banner> = async {
            client
                .get()
                .uri("/suspend")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange()
                .awaitBody<Banner>()
        }
        val deferredBanner2: Deferred<Banner> = async {
            client
                .get()
                .uri("/suspend")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange()
                .awaitBody<Banner>()
        }
        listOf(deferredBanner1.await(), deferredBanner2.await())
    }

    @GetMapping("/error")
    suspend fun error() {
        throw IllegalStateException()
    }

    @GetMapping("/cancel")
    suspend fun cancel() {
        throw CancellationException()
    }*/
}