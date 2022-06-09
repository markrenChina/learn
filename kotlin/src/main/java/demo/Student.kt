package demo

internal class Student(val mName: String, val mAge : Int) : Person(mName,mAge),Study {
    override fun readBook() {
        println("$mName is readBook" )
    }
}