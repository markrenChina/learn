package com.ccand99.r2dbc

import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.http.MediaType
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import kotlin.system.measureTimeMillis

@RestController
class cot (
    val customerRepository: CustomerRepository,
    val customerRepository2: CustomerRepository2,
    val connectionFactory: ConnectionFactory,
    val template: R2dbcEntityTemplate
) {

    @GetMapping("1234")
    fun aaa() = "success"

    @GetMapping(path = ["/sns/jscode2session"],produces = [MediaType.TEXT_PLAIN_VALUE])
    fun bbb(exchange: ServerWebExchange)
       = """
           {"errcode":40029,"errmsg":"invalid code, hints: [ req_id: NGcAhOyWf-tvx1tA ]"}
       """.trimIndent()


    @GetMapping("abc")
    fun abc() = customerRepository.findById(1)

    @GetMapping("bcd")
    suspend fun bdc() = customerRepository2.findById(2)

    @GetMapping("insert1")
    fun insert1(): Long {
        val time = System.currentTimeMillis()
        Flux.range(1,500).flatMap { customerRepository.save(Customer("abc","bcd")) }.subscribe()
        return System.currentTimeMillis() - time
    }

    @GetMapping("insert2")
    suspend fun insert2() = measureTimeMillis {
        (1 ..500).forEach{ _ -> customerRepository2.save(Customer("abc","bcd"))}
    }

    @GetMapping("insert3")
    suspend fun insert3() = measureTimeMillis {
        (1 ..500).asFlow().buffer().collect { customerRepository2.save(Customer("abc","bcd"))}
    }

    @GetMapping("insert4")
    fun insert4(): Long {
        val time = System.currentTimeMillis()
        (1..500).forEach { _ -> customerRepository.save(Customer("abc","bcd")).subscribe() }
        return System.currentTimeMillis() - time
    }

    @GetMapping("insert5")
    suspend fun insert5() = measureTimeMillis {
        foo().collect { it.await() }
    }

    suspend fun foo() = flow {
        coroutineScope {
            for (i in 1..500) {
                emit(async { customerRepository2.save(Customer("abc", "bcd")) })
            }
        }
    }

    @Bean
    fun initializer(): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
        return initializer
    }
}