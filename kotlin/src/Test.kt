import java.io.File

fun main() {
    /*val src = HashSet<Int>()
    src.add(1)
    src.add(12)
    println(src)
    src.toString().substring(1,src.toString().trim().length-1).split(",").forEach {
        println(it.trim().toInt())
    }

    var test: Int?= null
    test = 1;
    println(test)*/

    /*var calpoint = 0.0F
    calpoint /= 1;
    println(calpoint);*/

    //ZipCompress.zipFile2PathAdler32(File("D:\\temp\\testzip\\src.zip"),"D:\\temp\\testzip\\adl",5258211L)
    //ZipCompress.zipFile2Path2(File("D:\\temp\\testzip\\src.zip"),"D:\\temp\\testzip\\src")
    //val str = File("D:/temp/testzip/src/test/test.txt")
    //createParentDir(str)

    val testArray = floatArrayOf(0.1F,0.2F,0.3f,0.11F,0.21F,0.12F)
    sortl2h(testArray)
    //sorth2l(testArray)
    testArray.forEach {
        println(it)
    }
}

fun createParentDir(file: File): Boolean {
    return File(file.absolutePath.substringBeforeLast(File.separatorChar)).mkdirs()
}

fun sortl2h(array: FloatArray) {
    var index = 0
    for (i in 1 until array.size) {
        val temp = array[i]
        for (j in i downTo 1){
            if (temp < array[j-1]){
                array[j] = array[j-1]
            }else {
                index = j
                break
            }
        }
        array[index] = temp
    }
}

fun sorth2l(array: FloatArray) {
    var index = 0
    for (i in 1 until array.size) {
        val temp = array[i]
        for (j in i downTo 1){
            if (temp > array[j-1]){
                array[j] = array[j-1]
            }else {
                index = j
                break
            }
        }
        array[index] = temp
    }
}