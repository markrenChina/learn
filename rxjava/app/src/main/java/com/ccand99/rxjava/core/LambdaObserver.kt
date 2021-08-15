package com.ccand99.rxjava.core

class LambdaObserver<T>(
    private val mOnNext: (T) -> Unit,
    private val mOnComplete: ()->Unit ={},
    private val mOnError: (Throwable)-> Unit = {throw it},
    private val mOnSubscribe: (Disposable) ->Unit = {}
): Observer<T>,Disposable {

    override fun dispose() {}

    override fun onSubscribe(d: Disposable) {
        mOnSubscribe(d)
    }

    override fun onNext(t: T) {
        mOnNext(t)
    }

    override fun onError(e: Throwable) {
        mOnError(e)
    }

    override fun onComplete() {
        mOnComplete()
    }
}