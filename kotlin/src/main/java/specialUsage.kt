fun main() {
    /*println("带参数1000")
    判空模拟java三目运算(1000)
    println("不带参数")
    判空模拟java三目运算()
    println("测试三目运算1")
    模拟三目运算执行表达式(1000, 500)
    println("测试三目运算2")
    模拟三目运算执行表达式(600, 800)
    println("测试三目运算返回值1")
    测试三目运算返回值(900,700)
    println("测试三目运算返回值2")
    测试三目运算返回值(300,400)*/
    //println(测试三目运算执行函数后返回值(true))
    println(测试三目运算执行函数后返回值(false))

    //isTure 中间插run{}会先执行run里面的后判断
    val a : Int?= null
    val protocolVersion = (a == null) isTrue
         a.toString()
    ?: kotlin.run{
        "HTTP/1.1"
    }
    println(protocolVersion)
}

fun test(){
    throw Exception()
}

fun 判空模拟java三目运算(variable: Int? = null) {
    variable?.let { println(it) } ?: println("null")
}
//(a>b) ? a : b ;
fun 模拟三目运算执行表达式(var1: Int, var2: Int) {
    (var1 > var2) trueToDo{ println(var1) } ?: run { println(var2) }
}

fun 测试三目运算返回值(var1: Int, var2: Int) {
    val a = (var1 > var2) isTrue var1 ?: var2
    println(a)
}

fun 测试三目运算执行函数后返回值(bol: Boolean):String{
    return (bol) trueDoBack{
        print("true")
        "true".substring(0,2)
    }?: run{
        println("false")
        "false".substring(0,3)
    }
}

public inline infix fun Boolean.trueToDo( noinline block: ()->Unit): Unit?= if (this) block() else null

public inline infix fun<T> Boolean.trueDoBack(crossinline block: ()-> T): T?= if (this) block() else null

public infix fun<T> Boolean.isTrue(var1 : T): T?= if (this) var1 else null
