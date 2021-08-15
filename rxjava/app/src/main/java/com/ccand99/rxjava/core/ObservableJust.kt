package com.ccand99.rxjava.core


class ObservableJust<T>(private val mItem: T) : Observable<T>(){

    override fun subscribeActual(observer: Observer<T>) {
        val sd = ScalarDisposable(observer,mItem)
        observer.onSubscribe(sd)
        sd.run()
    }

}