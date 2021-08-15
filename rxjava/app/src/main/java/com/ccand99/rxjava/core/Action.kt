package com.ccand99.rxjava.core

/**
 * 在kotlin 中用()-> Unit代替
 */
@FunctionalInterface
@Deprecated(message = "()-> Unit")
interface Action {

    @Throws(Throwable::class)
    fun run()
}