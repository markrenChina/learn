package demo.pattern

open class Person(val name: String, val age: Int)

class Student(name: String, age: Int) : Person(name, age)
class Teacher(name: String, age: Int) : Person(name, age)

interface MyClass<T> {

}

//协变添加out之后， 范型只能出现在返回类型上
class SimpleData<out T>(val data: T?) {
 //   private  = null
//    fun set(t: T?) {
//        data = t;
//    }

    fun get() = data
}




fun main() {
    val student = Student("Tom",19)
    val data = SimpleData<Student>(student)
    //data.set(student)
    //Student 是Person 子类，但是SimpleData<Student> 不是SimpleData<Person> 子类
    //协变 让SimpleData<Student> 成为 SimpleData<Person> 子类
    handleSimpleData(data)

}

fun handleSimpleData(data: SimpleData<Person>){
    //val teacher = Teacher("Jack",35);
    //data.set(teacher)
    val studentData = data.get()
    println(studentData)
}