package hannuta

import java.util.Stack

class Hannuota(val size: Int) {
    val stack1 : Stack<Int> = Stack<Int>()
    val stack2 : Stack<Int> = Stack<Int>()
    val stack3 : Stack<Int> = Stack<Int>()

    init {
        for (i in size downTo  1 ) {
            stack1.push(i)
        }
    }

    private fun printTitle(): String = StringBuilder().apply {
        append("A:")
        for (i in 1..size * 2) {
            append(" ")
        }
        append("B:")
        for (i in 1..size * 2) {
            append(" ")
        }
        append("C:")
        for (i in 1..size * 2) {
            append(" ")
        }
        append("\n")
    }.toString()

    fun move(src: Stack<Int>,target: Stack<Int>){
        val tmp = src.pop()
        if (target.isEmpty() || target.peek() > tmp){
            target.push(tmp)
            println(this)
        }else {
            System.err.println("错误的操作")
            src.push(tmp)
        }
    }

    fun a2b(size: Int = 1){
        if (size == 1){
            move(stack1,stack2)
        }else{
            val temp = size-1
            a2c(temp)
            a2b()
            c2b(temp)
        }
    }
    fun a2c(size: Int = 1){
        if (size == 1){
            move(stack1,stack3)
        }else {
            val temp = size-1
            a2b(temp)
            a2c()
            b2c(temp)
        }
    }
    fun b2a(size: Int = 1){
        if (size == 1){
            move(stack2,stack1)
        }else {
            val temp = size-1
            b2c(temp)
            b2a()
            c2a(temp)
        }
    }
    fun b2c(size: Int = 1){
        if (size == 1){
            move(stack2,stack3)
        }else {
            val temp = size-1
            b2a(temp)
            b2c()
            a2c(temp)
        }
    }
    fun c2b(size: Int = 1){
        if (size ==1){
            move(stack3,stack2)
        }else{
            val temp = size-1
            c2a(temp)
            c2b()
            a2b(temp)
        }
    }
    fun c2a(size: Int = 1){
        if (size == 1){
            move(stack3,stack1)
        }else{
            val temp = size-1
            c2b(temp)
            c2a()
            b2a(temp)
        }
    }


    override fun toString(): String {
        val maxSize = size*2
        return StringBuilder().apply {
            append(printTitle())
            for (i in  (size-1) downTo 0 ) {
                append(stack1.printLine(i,maxSize))
                append(stack2.printLine(i,maxSize))
                append(stack3.printLine(i,maxSize))
                append("\n")
            }
        }.toString()
    }
}


fun Stack<Int>.printLine(index : Int,maxSize : Int) : String = StringBuilder().apply {
    append(" ")
    if (this@printLine.isEmpty() || index >= this@printLine.size){
        for (i in 1..maxSize) {
            append(" ")
        }
    }else {
        val value = this@printLine.elementAt(index)
        val blackSize = (maxSize - value*2)/2
        for (i in 1..blackSize) {
            append(" ")
        }
        for (i in 1..value * 2) {
            append("-")
        }
        for (i in 1..blackSize) {
            append(" ")
        }
    }
    append(" ")
}.toString()

fun main(){
    val han = Hannuota(4)
    println(han)
    han.a2c(4)
}