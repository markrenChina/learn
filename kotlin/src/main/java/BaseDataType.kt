fun main(){
    val byte = 255u;
    //val byteHex1 : Byte = 0XFF;
    val byteHex2 = 0XFFu;
    println("byteHex2 = $byteHex2")

    val short : UShort = 65535u;
    val long :ULong = 2511uL

    val sampleshow : Int = 123_456;
    println( sampleshow)

    //?可空声明的时包装类 ，包装类的地址与基本数据类型不同，==比较值相同 ===比较地址不同

    //使用print 会导致text2的第一行没有被trim
    val text1 = """
            for (c in "foo")
            print(c)
            """
    println(text1)

    //使用print 会trim掉最后一行
    val text2 = """
            |for (c in "foo")
            |print(c)
            |abc
            |def
            """.trimMargin("|")
    println(text2)
}
