import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*
import kotlin.random.Random

class ProxyTest {
}

class TraceHandle(
    private val target: Any
) : InvocationHandler {

    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
        //print implicit argument
        print(target)
        //print method name
        print(".${method.name}(")
        //print explicit arguments
        args?.let {
            for (i in it.indices) {
                print(it[i])
                if (i < args.size - 1) {
                    print(", ")
                }
            }
        }
        print(")")
        return method.invoke(target, args)
    }
}

fun main() {
    val elements = Array<Any>(1000) {
        val handler = TraceHandle(it + 1)
        Proxy.newProxyInstance(
            ClassLoader.getSystemClassLoader(),
            arrayOf(java.lang.Comparable::class.java),
            handler
        )
    }
    //construct a random integer
    val key: Int = Random.Default.nextInt(elements.size + 1)

    //search for the key
    val result = Arrays.binarySearch(elements, key)

    //print match if found
    if (result > 0) {
        println(elements[result])
    }
}


