
const val BITS_OF_BYTE = 8
const val POLYNOMIAL = 0xA001
const val INITIAL_VALUE = 0xFFFF
const val FF  = 0xFF

fun ByteArray.crc16(): ByteArray{
    var res = INITIAL_VALUE
    for (data in this) {
        res = res xor (data.toInt() and FF)
        for (i in 0 until BITS_OF_BYTE) {
            res = if (res and 0x0001 == 1) res shr 1 xor POLYNOMIAL else res shr 1
        }
    }
    val highByte: Byte = (res  shr 8 and FF).toByte()
    val lowByte: Byte = (res and FF).toByte()
    //return this.plus(highByte).plus(lowByte)
    return byteArrayOf(*this,highByte,lowByte)
}

fun IntArray.crc16(): ByteArray{
    val byteArray = ByteArray(this.size+ 2)
    var res = INITIAL_VALUE
    for (index in this.indices) {
        res = res xor this[index]
        byteArray[index] = this[index].toByte()
        for (i in 0 until BITS_OF_BYTE) {
            res = if (res and 0x0001 == 1) res shr 1 xor POLYNOMIAL else res shr 1
        }
    }
    val highByte: Byte = (res  shr 8 and FF).toByte()
    val lowByte: Byte = (res and FF).toByte()
    byteArray[this.size] = highByte
    byteArray[this.size + 1] = lowByte
    return byteArray
}

fun ByteArray.crc16Verify(): Boolean{
    if (this.size < 3) return false
    var res = INITIAL_VALUE
    for (index in 0..this.size-3) {
        res = res xor (this[index].toInt() and FF)
        for (i in 0 until BITS_OF_BYTE) {
            res = if (res and 0x0001 == 1) res shr 1 xor POLYNOMIAL else res shr 1
        }
    }
    val highByte: Byte = (res  shr 8 and FF).toByte()
    val lowByte: Byte = (res and FF).toByte()
    return highByte == this[this.size - 2] && lowByte == this[this.size - 1]
}
