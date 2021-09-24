package com.ccand99.rxjava

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ccand99.rxjava.core.Observable
import com.ccand99.rxjava.core.Schedulers

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<>()
        Log.i(TAG, "onCreate: ")
        Observable.just("markrenChina")
            /*.subscribe(object : ObserverImpl<String>() {

                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                    Log.d(TAG, "onSubscribe")
                }
                
                override fun onNext(t: String) {
                    Log.i(TAG, "onNext: $t")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.e(TAG, "onError: $e")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

            })*/
            .map {
                isMainThread("map1")
                "$it add map1"
            }
            .map {
                isMainThread("map2")
                "$it add map2"
            }
            .subscribeOn(Schedulers.io)
            .observeOn(Schedulers.main)
            .subscribe {
                isMainThread("subscribe")
                Log.i(TAG, "end: $it")
            }
    }


    private fun isMainThread(tag: String) =
        if (Looper.getMainLooper() == Looper.myLooper()) {
            Log.i(TAG, "$tag 在 主 线程中执行")
        } else {
            Log.d(TAG, "$tag 在 子 线程中执行")
        }
}