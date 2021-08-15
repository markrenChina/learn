package com.ccand99.rxjava.core

/**
 * 用 (T) -> Unit 替代
 */
@FunctionalInterface
@Deprecated(message = "(T) -> Unit")
interface Consumer<T> {
    @Throws(Throwable::class)
    fun accept(t: T)
}