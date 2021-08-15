package com.ccand99.rxjava.core

class ObservableMap<T, R>(
    private val source: ObservableSource<T>,
    private val mBlock: (T) -> R)
    : Observable<R>() {

    override fun subscribeActual(observer: Observer<R>) {
        source.subscribe(MapObserver(observer,mBlock))
    }

}
