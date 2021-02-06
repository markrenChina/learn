import java.util.*

val eps = 1E-10
fun main(){
    println(System.currentTimeMillis())
    println(Date().time)
    println(findFixPoint())
}
//tailrec 优化递归
tailrec fun findFixPoint(x: Double = 1.0 ): Double =
    if (Math.abs(x - Math.cos(x)) < eps) {
        x
    }else{
        findFixPoint(Math.cos(x))
    }