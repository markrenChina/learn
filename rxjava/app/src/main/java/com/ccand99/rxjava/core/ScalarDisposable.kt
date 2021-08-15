package com.ccand99.rxjava.core

import java.util.concurrent.atomic.AtomicInteger


class ScalarDisposable<T>(
    val observer: Observer<in T>,
    val value: T
) : AtomicInteger(), Disposable,Runnable {

    companion object{
        const val START = 0
        const val FUSED = 1
        const val ON_NEXT = 2
        const val ON_COMPLETE = 3
    }

    override fun dispose() {
        set(ON_COMPLETE)
    }

    override fun run() {
        if (get() == START && compareAndSet(START, ON_NEXT)){
            observer.onNext(value)
            if (get() == ON_NEXT) {
                lazySet(ON_COMPLETE)
                observer.onComplete()
            }
        }

    }

    override fun toByte(): Byte = get().toByte()

    override fun toChar(): Char = get().toChar()

    override fun toShort(): Short = get().toShort()

}