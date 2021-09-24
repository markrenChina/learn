package com.ccand99.androidexample

class Kout {
    val msg: StringBuilder = StringBuilder()
    fun endl(){
        println(msg.toString())
    }
    operator fun plus(str:String)= this.also { it.msg.append(str) }
}

fun Kout.and(str:String) = this.also { it.msg.append(str) }