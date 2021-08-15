package com.ccand99.rxjava.core

/**
 * 线程调度类
 */
class ObservableSchedulers<T>(
    private val mSource: Observable<T>,
    private val mScheduler : Schedulers)
    : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        mScheduler.scheduleDirect{
            mSource.subscribe(observer)
        }
    }
}
