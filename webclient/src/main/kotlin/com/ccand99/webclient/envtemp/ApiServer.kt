package com.ccand99.webclient.envtemp

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiServer(val value: String = "")