package demo.pattern

//T 只能出现在入参的时候，不能出现在返回的时候
interface Transformer<in T> {
    fun transform(t: T) : String
}

fun main(){
    val trans = object : Transformer<Person> {
        override fun transform(t: Person): String {
            return "${t.name} ${t.age}"
        }
    }
    //逆变 让SimpleData<Person> 成为SimpleData<Student> 子类
    handleTransformer(trans)
}

fun handleTransformer(trans : Transformer<Student>) {
    val student= Student("Tom",19)
    val res = trans.transform(student)
    println(res)
}