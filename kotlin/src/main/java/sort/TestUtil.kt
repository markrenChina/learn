package sort

import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.timerTask
import kotlin.random.Random
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun createBigArray(size :Int): Array<Int> {
    val random = Random(System.currentTimeMillis())
    val max = size + (size ushr 1)
    val array : Array<Int> = Array(size){
        random.nextInt(max)
    }
    return array;
}

fun createSortBigArray(size: Int,outOrderSize : Int): Array<Int>{
    val random = ThreadLocalRandom.current()
    val array = Array(outOrderSize){
        random.nextInt(size)
    }
    val bakArray = Array(size){
        it
    }
    for (i in array){
        bakArray[i] = random.nextInt( size + (size ushr 1) )
    }
    return bakArray
}

fun createRepeatBigArray(size: Int,repeatSize : Int): Array<Int>{
    val random = ThreadLocalRandom.current()
    val array = Array(repeatSize){
        random.nextInt(size)
    }
    val bakArray = Array(size){
        100
    }
    for (i in array){
        bakArray[i] = random.nextInt( size + (size ushr 1) )
    }
    return bakArray
}

fun verifySort(array: Array<Int>) {
    for (i in 1 until array.size){
        if(array[i - 1] > array[i]){
            throw RuntimeException("i - 1 value = ${array[i - 1]} , i value = ${array[i]},not sort")
        }
    }
}

fun testArray(array: Array<Int>,name : String,block: ()-> Unit) {
    val time = measureTimeMillis {
        block()
    }
    println("$name 的执行时间 $time 毫秒")
    verifySort(array)
}

fun swap(array: Array<Int>,left: Int ,right: Int){
    val tmp = array[right]
    array[right] = array[left]
    array[left] = tmp
}