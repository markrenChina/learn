
import java.util.*

fun ByteArray.toHexString(): String {
    return StringBuilder().run {
        this@toHexString.forEach {
            this.append(Integer.toHexString((it.toInt() and 0xFF or 0x100)).substring(1, 3))
        }
        this.toString().uppercase(Locale.ROOT)
    }
}

//Little-Endian 高在前，低也后
fun twoByte2Int(height: Byte, low:Byte) = (height.toInt().and(0xff).shl(8)) or (low.toInt() and 0xff)

fun bytes2IntHL(bytes: ByteArray, startIndex : Int) =
    fourByte2IntHL(
        bytes[startIndex],
        bytes[startIndex + 1],
        bytes[startIndex + 2],
        bytes[startIndex + 3]
    )

fun fourByte2IntHL(vararg bytes: Byte): Int {
    assert(bytes.size >= 4)
    var res = 0
    for (i in 0..3){
        res = res.shl(8) or bytes[i].toInt().and(0xFF)
    }
    return res
}

fun Int.get32Bit24to32Byte() = (this ushr 24).toByte()

fun Int.get32Bit16to24Byte() = ((this ushr 16) and 0xff).toByte()

fun Int.get32Bit8to16Byte() = ((this ushr 8) and 0xff).toByte()

fun Int.get32BitLow8Byte() = (this and 0xff).toByte()




