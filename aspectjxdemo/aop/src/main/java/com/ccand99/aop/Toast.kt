package com.ccand99.aop

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Toast(val value: String = "")
