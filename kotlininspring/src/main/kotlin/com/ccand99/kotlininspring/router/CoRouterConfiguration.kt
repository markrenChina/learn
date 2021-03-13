package com.ccand99.kotlininspring.router


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.POST

/**
 * @author Sebastien Deleuze
 */
/*
@Configuration
class CoRouterConfiguration {

    @Bean
    fun mainRouter(userHandler: UserHandler) = coRouter {
        accept(TEXT_HTML).nest {
            (GET("/user/") or GET("/users/")).invoke(userHandler::findAllView)
            GET("/users/{login}", userHandler::findViewById)
        }
        accept(APPLICATION_JSON).nest {
            (GET("/api/user/") or GET("/api/users/")).invoke(userHandler::findAll)
            POST("/api/users/", userHandler::create)
        }
    }

}*/
