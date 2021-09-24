import java.io.File
import java.io.FileInputStream
import java.util.concurrent.ThreadLocalRandom
import java.util.zip.Adler32

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

    //93411726
    ZipCompress.zipFile2PathAdler32(File("E:\\test\\batchNumberInfo.dat"),"E:\\test",452420698L)

    val bytes = File("E:\\test\\batchNumberInfo.dat").readBytes()
    val checksumEngine = Adler32()
    checksumEngine.update(bytes, 0, bytes.size)
    val checksum = checksumEngine.value
    println(checksum)

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
    /*println(Integer.toBinaryString( 16))
    println( Integer.toBinaryString( 16 ushr 2)+"  " + (16 ushr 2))
    println( Integer.toBinaryString( 16 shr 2)+"  " + (16 shr 2))
    println("========================================")
    println(Integer.toBinaryString( -16))
    println( Integer.toBinaryString( -16 ushr 2)+"  " + (-16 ushr 2))
    println( Integer.toBinaryString( -16 shr 2)+"  " + (-16 shr 2))*/

    /*val file= File("E:\\ideaproject\\learn\\kotlin\\src\\main\\java\\videos.json")
    if (file.exists()) {
        val list: VideoList? = file.jsonRead()
        list?.videos?.forEach {
            println(it)
        } ?: println("无")

        val file = File("E:\\ideaproject\\learn\\kotlin\\src\\main\\java\\copy.json")
        //file.jsonWrite(list)
        file.asyncJsonWrite(list)
    }else{
        println("找不到文件")
    }
    TimeUnit.SECONDS.sleep(2)*/

    //val doubleArray = doubleArrayOf(1.0,2.0,30.0,54.0,99.0)
    //println(doubleArray.joinToString(separator=",",prefix="[",postfix="]"))

    /*val targetFileUri = "http://apkst.zhipu-china.com/8port_V1.1.16_20210527_release"
    val extensionName = targetFileUri.substringAfterLast(".")
    val tempFile = targetFileUri.replace(extensionName,"part")
    println(extensionName)
    println(tempFile)*/

    /*val res1 = RegularUtils.checkUrlFROMHttps("https://ab.zdaa.cn:8818/api")
    println(res1)*/
    val data = 520301562
    println(fourByte2IntHL(
        0x00,
        (0xA3).toByte(),
        (0xE3).toByte(),
        (0x9B).toByte()).toString())

    /*val data = intArrayOf( 5877,5709,5642,5592,5564,5524,5506,5465,5430,5381,5342,5314,5259,5190,5126,5020,4995,4944,4844,4770,4696,4621,4531,4451,4367,4260,4167,4080,3951,3854,3757,3668,3582,3520,3459,3408,3364,3327,3257,3220,3156,3149,3074,3079,3016,2993,2963,2940,2902,2872,2863,2845,2801,2765,2774,2760,2757,2751,2747,2749,2765,2776,2810,2857,2900,2968,3026,3133,3247,3373,3511,3688,3858,4033,4143,4281,4313,4260,4133,3953,3755,3574,3442,3335,3258,3226,3178,3137,3121,3130,3090,3080,3074,3084,3084,3103,3118,3127,3163,3226,3255,3313,3353,3423,3540,3621,3741,3906,4075,4276,4498,4762,4987,5223,5395,5457,5472,5434,5340,5321,5281,5331,5433,5581,5734,5964,6178,6402,6651,6905,7227,7543,7942,8315,8763,9236,9746,10319,10855,11516,12141,12832,13512,14193,14963,15661,16390,17054,17696,18332)
    var bytes = ByteArray(0)
    data.map {
        byteArrayOf(it.get32BitLow8Byte(),it.get32Bit8to16Byte())
    }.forEach { bs ->
        bytes = bytes.plus(bs)
    }
    println(bytes.toHexString())
    val hex = "F5164D160A16D815BC1594158215591536150515DE14C2148B14461406149C1383135013EC12A21258120D12B31163110F11A4104710F00F6F0F0E0FAD0E540EFE0DC00D830D500D240DFF0CB90C940C540C4D0C020C070CC80BB10B930B7C0B560B380B2F0B1D0BF10ACD0AD60AC80AC50ABF0ABB0ABD0ACD0AD80AFA0A290B540B980BD20B3D0CAF0C2D0DB70D680E120FC10F2F10B910D910A4102510710FAB0EF60D720D070DBA0C9A0C6A0C410C310C3A0C120C080C020C0C0C0C0C1F0C2E0C370C5B0C9A0CB70CF10C190D5F0DD40D250E9D0E420FEB0FB41092119A127B1367141315511560153A15DC14C914A114D3143915CD1566164C1722180219FB19F91A3B1C771D061F7B203B22142412264F28672AFC2C6D2F2032C8347137733A2D3D06409E4220459C47"
    val intArray = IntArray(150)
    var index = 0
    for (i in 0..149) {
        intArray[i] = twoByte2Int(bytes[index + 1], bytes[index])
        index += 2
    }
    println(intArray.joinToString(separator = ",", prefix = "[", postfix = "]"))*/

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
