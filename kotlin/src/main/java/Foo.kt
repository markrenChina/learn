data class Foo(
    var a: Int?
) {

    init {
        if (a !in 0..200){
            a = 1
        }
    }
}

