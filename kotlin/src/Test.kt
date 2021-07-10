import java.io.File
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.TemporalAccessor
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

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

    //val testArray = floatArrayOf(0.1F,0.2F,0.3f,0.11F,0.21F,0.12F)
    //sortl2h(testArray)
    //sorth2l(testArray)
    //testArray.forEach {
        //println(it)
    //}

    //val start = LocalDate.parse("2021-06-15").atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
    //println(1623772800L-start)

    //println(lerp(2.0F,4.0F,0.1F))

    //val byteArray = byteArrayOf(0x15,0x06,0x00,0x54,0x00,0x02)
    //println(byteArray.crc16().toHexString())

    //val attributes = pickTwo("good","fast","cheap")
    //val attributes = toArray("good","fast","cheap")
    //println(attributes.contentToString())
    println(Integer.toBinaryString( 16))
    println( Integer.toBinaryString( 16 ushr 2)+"  " + (16 ushr 2))
    println( Integer.toBinaryString( 16 shr 2)+"  " + (16 shr 2))
    println("========================================")
    println(Integer.toBinaryString( -16))
    println( Integer.toBinaryString( -16 ushr 2)+"  " + (-16 ushr 2))
    println( Integer.toBinaryString( -16 shr 2)+"  " + (-16 shr 2))
}

fun <T> toArray(vararg args: T) = args

inline fun <reified T> pickTwo(a : T, b : T, c :T) =
    when(ThreadLocalRandom.current().nextInt(3)){
        0 -> toArray(a,b)
        1 -> toArray(a,c)
        else -> toArray(b,c)
    }



fun getIntArrayMinIndexBetween(src: IntArray, srcPos: Int, length: Int): Int {
    //if (srcPos + length >= src.size ) {
    //return -1
    //}
    var index = if (srcPos >= 0) srcPos else 0
    for (i in index until  if (srcPos + length < src.size) srcPos + length else src.size) {
        if (src[index] > src[i]) {
            index = i
        }
    }
    return index
}

fun createParentDir(file: File): Boolean {
    return File(file.absolutePath.substringBeforeLast(File.separatorChar)).mkdirs()
}

/**
 * Linearly interpolate between two offsets.
 *
 * @param start 起始值
 * @param stop 结束值
 * @param fraction 偏移量
 */

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
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