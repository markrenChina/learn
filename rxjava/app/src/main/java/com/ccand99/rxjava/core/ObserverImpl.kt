package com.ccand99.rxjava.core


/**
 * 观察者
 */
abstract class ObserverImpl<T> :Observer<T>{

    private lateinit var upstream: Disposable

    override fun onSubscribe(d:  Disposable) {
        upstream = d
        onStart()
    }

    protected fun cancel() {
        upstream.dispose()
    }

    protected open fun onStart() {}

}