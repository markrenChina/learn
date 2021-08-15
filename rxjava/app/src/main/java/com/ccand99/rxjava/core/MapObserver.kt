package com.ccand99.rxjava.core

class MapObserver<T, R>(
    private val mSource: Observer<R>,
    val mBlock: (T) -> R) : Observer<T> {
    override fun onSubscribe(d: Disposable) {
        mSource.onSubscribe(d)
    }

    override fun onNext(t: T) {
        mSource.onNext(mBlock(t))
    }

    override fun onError(e: Throwable) {
        mSource.onError(e)
    }

    override fun onComplete() {
        mSource.onComplete()
    }

}
