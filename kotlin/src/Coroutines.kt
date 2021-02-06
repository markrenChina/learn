import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis


fun test1() {
    GlobalScope.launch {
        delay(1000L)
        print("World!")
    }
    print("Hello,")
    runBlocking {
        delay(2000L)
    }
}

fun test2() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            //while (i < 15) {
            //isActive 显示的检查状态

            while (isActive) {
                //一个执行计算任务的循环，只是为了占用cpu
                //每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L)
        println("main: I'm tired of wating!")
        //取消一个作业并且等待它结束
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}

fun test3() = runBlocking {
    launch {
        println("begin")
        withTimeout(1300L) {
            repeat(1000) {
                println("I'm sleeping $it")
                delay(500L)
            }
        }
        println("end")
    }
}


suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    return 29
}

/**
 * 组合挂起函数
 */
fun test4() = runBlocking {
    val time = measureTimeMillis {
        //顺序执行2个suspend函数
        //val one = doSomethingUsefulOne()
        //val two = doSomethingUsefulTwo()

        //并发执行2个suspend函数
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

/**
 * 调度器
 */
fun test5() = runBlocking {
    launch {
        //运行在父协程的Context，在这里是runBlocking的主协程
        println("main runBlocking  : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) {
        //不受限--工作在主线程
        println("Unconfined  : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) {
        //获得默认的调度器
        println("Default  : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) {
        //获得一个先的线程
        println("newSingleThreadContext  : I'm working in thread ${Thread.currentThread().name}")
    }
}

fun foo(): Flow<Int> = flow{
    //流构建器
    for(i in 1..3){
        delay(100)
        emit(i)
    }
}

fun testFlow() = runBlocking {
    //启动并发协程验证主线程并未阻塞
    launch {
        for (k in 1..3 ){
            println("I'm not blocked $k")
            delay(100)
        }
    }
    foo().collect { println(it) }
}

suspend fun performRequest(request: Int):String{
    delay(1000)
    return "response $request"
}

fun testAsFlow() = runBlocking {
    /**
     * .asFlow()将各种集合与序列转换为流。
     * 流的过渡操作符，如map，filter 应用于上流游，返回下流游
     * 转换操作符 如 transform 适用复杂转换
     * 限长过渡操作符 如 take 在流触及相应限制时候取消
     * 末端操纵符collect toList toSet 可以转换成集合
     * first()获取第一个值，single()确保发射单值，reduce()、fold()将流约到单值
     */
    (1..3).asFlow()
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }
}

fun log(msg:String) = println("[${Thread.currentThread().name}] $msg")

fun foo2(): Flow<Int> = flow{
    for (i in 1..3){
        Thread.sleep(100)
        log("Emitting $i")
        emit(i)
    }
}.flowOn(Dispatchers.Default)

/**
 * buffer 可以并行发射和收集结果
 */
fun testFlowBuffer() = runBlocking {
    val time = measureTimeMillis {
        foo2().buffer()
            .collect {
                delay(300)
                println(it)
            }
    }
    println("Collected in $time ms")
}

/**
 * Channel
 */
fun testChannel() = runBlocking {
    val channel = Channel<Int>()
    launch {

        for ( x in 1..5) {
            channel.send(x * x)
        }
        // 结束发送
        channel.close()
    }
    // 这里打印5次收到的整数
    repeat(5) { println("print from repeat ${channel.receive()}")}

    //打印所有的通道元素
    for (y in channel) {
        println("print from for $y")
    }
    println("Done!")
}

/**
 * produce 构建通道
 */
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x )
}

fun testProduce() = runBlocking {
    val square = produceSquares()
    square.consumeEach { println(it) }
    println("Done!")
}

fun main() {
    //test1()
    //test2()
    //test3()
    //test4()
    //test5()
    //testFlow()
    //testAsFlow()
    testFlowBuffer()
    //testFlowBuffer2()
    //testChannel()
    //testProduce()
}

fun testFlowBuffer2() = runBlocking {
    val arry = arrayOf(1,2,3,4,5)
    val time = measureTimeMillis {
        arry.forEach { _ ->
            delay(100)
        }
    }
    println("Collected in $time ms")
    val time2 = measureTimeMillis {
        flow<Int> {
            for (e in arry) {
                delay(100)
                emit(e)
            }
        }.buffer().collect {  }
    }

    println("Collected in $time2 ms")
}
