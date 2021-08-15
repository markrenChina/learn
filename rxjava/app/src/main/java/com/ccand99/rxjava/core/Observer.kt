package com.ccand99.rxjava.core


interface Observer<T> {

    fun onSubscribe(d: Disposable)

    fun onNext(t: T)

    fun onError(e: Throwable)

    fun onComplete()
}