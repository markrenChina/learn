package rxjava3

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable

fun listener(int: Int?): Int? {
    return int
}

fun main (){
    val flow = Flowable.fromCallable {

    }
}