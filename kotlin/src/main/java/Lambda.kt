fun main(){
    val items = listOf(1,2,3,4,5,6,7,8)
    items.fold(0,{acc,i->
        println("acc = $acc , i = $i")
        acc + i
    })
    //字符串拼接
    val joinedToString = items.fold("Elements:",{acc, i -> "$acc $i" })
    println(joinedToString)

    //高阶函数调用 1*1*...*8
    val product = items.fold(1,Int::times)
    print(product)
}
