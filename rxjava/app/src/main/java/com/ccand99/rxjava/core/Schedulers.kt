package com.ccand99.rxjava.core

import android.os.Handler
import android.os.Looper

sealed class Schedulers{

    abstract fun scheduleDirect(runnable: Runnable)

    object io : Schedulers(){
        override fun scheduleDirect(runnable: Runnable){
            Thread(runnable).start()
        }
    }
    object main : Schedulers(){
        override fun scheduleDirect(runnable: Runnable){
            Handler(Looper.getMainLooper()).post(runnable)
        }
    }
}
