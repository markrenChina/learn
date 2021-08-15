package com.ccand99.rxjava.core


/**
 * 被观察者对象
 */
abstract class Observable<T>
    : ObservableSource<T>{

    companion object{
        fun <T> just(item: T): Observable<T> = ObservableJust(item)
    }

    protected abstract fun subscribeActual(observer: Observer<T>)

    fun subscribe(onNext: (T) -> Unit): Disposable {
        val ls = LambdaObserver(onNext)
        subscribe(ls)
        return ls
    }

    override fun subscribe(observer: Observer<T>) {

        runCatching {
            subscribeActual(observer)
        }.exceptionOrNull()?.let { e->
            observer.onError(e)
        }
        observer.onComplete()
    }

    fun<R> map(block: (T) -> R): Observable<R> =
        ObservableMap(this,block)

    fun subscribeOn(scheduler : Schedulers): Observable<T>  =
        ObservableSchedulers(this,scheduler)

    fun observeOn(scheduler: Schedulers) =
        ObservableObserveOn(this,scheduler)


}
