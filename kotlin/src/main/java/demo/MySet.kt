package demo

class MySet<T>(val helperSet: HashSet<T>) : Set<T> by helperSet{
    var stauts  by  Status()
    //lazy 是一个高阶函数，通过lambda表达式创建一个代理对象
    val stauts2 by Later {
        println(" stauts2 被赋值 ")
        1*100-200+500
    }

//    init {
//        println("set 构造函数完成")
//        val a = Set()
//    }
//
//    override fun isEmpty(): Boolean {
//        val clazz = T::class.java
//        return false
//    }
}