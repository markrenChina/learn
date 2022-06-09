package demo

import demo.Result

sealed class Result
class Success(val msg:String,val code : Int) : demo.Result()
class Fail(val msg:String) : demo.Result()


fun getResultMsg(result: demo.Result) = when(result) {
    is Success -> {}
    is Fail ->{}
}

fun main(){

}