import java.lang.reflect.ParameterizedType

class Reflect {
    fun method() : List<Map<String,Int>> { return listOf() }
}

//fun<L,M,S,I> create(list: Class<L>,map: Class<M>,str: Class<S>,i : Class<I>

fun main() {
    val reflect = Reflect()
    val clazz: Class<*> = reflect::class.java
    val method = clazz.getMethod("method")
    val returnType = method.returnType // interface java.util.List
    val genericReturnType = method.genericReturnType //java.util.List<java.util.Map<java.lang.String, java.lang.Integer>>
    val className = genericReturnType.typeName //java.util.List<java.util.Map<java.lang.String, java.lang.Integer>>
    val intClass: Class<*> = Class.forName("java.lang.Integer")
    val stringClass: Class<*> = Class.forName("java.lang.String")
    val mapClass: Class<Class<*>> = Class.forName("java.util.Map") as Class<Class<*>>
    val listClass: Class<*> = Class.forName("java.util.List")
    println(intClass)
    println(stringClass)
    println(mapClass)
    println(listClass)

    val resultV1Class :Class<Class<*>> = Class.forName("ResultV1") as Class<Class<*>>
    println(resultV1Class)
    val actualTypeArguments = (genericReturnType as ParameterizedType).actualTypeArguments
    println(actualTypeArguments[0])
}