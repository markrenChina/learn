package com.ccand99.rxjava.core

class ObservableObserveOn<T>(
    private val mSource: ObservableSource<T>,
    private val mScheduler : Schedulers
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        mSource.subscribe(ObserveOnObserver(observer))
    }

    inner class ObserveOnObserver(
        private val parentObserver : Observer<T>,
    ) : Observer<T>{
        override fun onSubscribe(d: Disposable) {
            parentObserver.onSubscribe(d)
        }

        override fun onNext(t: T) {
            mScheduler.scheduleDirect(){
                parentObserver.onNext(t)
            }
        }

        override fun onError(e: Throwable) {
            parentObserver.onError(e)
        }

        override fun onComplete() {
            parentObserver.onComplete()
        }

    }
}