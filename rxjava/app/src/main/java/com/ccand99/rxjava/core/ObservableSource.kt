package com.ccand99.rxjava.core

interface ObservableSource<T> {

    fun subscribe(observer: Observer<T>)
}