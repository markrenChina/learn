class Test(arg1: Int ,arg2: Int) {

    operator fun plus(v:Test2){
        instance() + Test2(1,2)
    }

    fun instance() = Test(3, 2)
}