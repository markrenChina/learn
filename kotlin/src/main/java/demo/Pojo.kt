package demo

data class Pojo(
    val test: String,
    val array : ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pojo

        if (test != other.test) return false
        if (!array.contentEquals(other.array)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = test.hashCode()
        result = 31 * result + array.contentHashCode()
        return result
    }
}
