package fiber

import kotlinx.coroutines.*

fun  log(obj : Any ) = println("[${Thread.currentThread().name}] --- $obj")

fun main() = runBlocking{
    //启动一个线程 1MB 涉及到系统调用
//    val ts = ArrayList<Thread>(1000_000)
//    (0..1000_000).forEach {
//        ts.add(Thread{
//            Thread.sleep(10000)
//            log("hello world3 ${Runtime.getRuntime().totalMemory()}")
//        })
//        ts.last().start()
//    }
//    (0..1000_000).forEach{
//        ts[it].join()
//    }


    val res = withContext(Dispatchers.IO) {
        delay(1000)
        5+5
    }
    println(res)
    //println("*" * 20)

    }
