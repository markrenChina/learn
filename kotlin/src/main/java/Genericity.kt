import java.util.*

fun getGenericity(): Class<*> {
    val foo = ArrayList<Set<String>>()
    
    return foo::class.java
}

fun main() {
    getGenericity()
}