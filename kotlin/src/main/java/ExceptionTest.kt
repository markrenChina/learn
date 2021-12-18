import java.io.IOException
import kotlin.jvm.Throws

fun main() {
    try {
        println("调用异常方法之前")
        throwE()
        println("调用异常方法之后")
    }catch (e : Exception){
        println("捕获Exception异常")
    }finally {
        println("main finally")
    }
}

@Throws(RuntimeException::class)
fun throwE(){
    try {
        //throw RuntimeException("throw 异常") //return 了当前调用
        throw IOException()
        println("throw 一个异常未捕获的方法之后")
    }catch (e: IOException){
        println("捕获IOException异常")
    }finally {
        //即使在上一层被捕获也会继续执行，即使没有异常也会执行
        //try中的return依然会触发finally语句块，如果finally中也有return，会遮蔽try中的结果。
        println("throwE finally")

    }
    //IOException 会执行 RuntimeException不会执行
    println("throwE 继续被执行")
}