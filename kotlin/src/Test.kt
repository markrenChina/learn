

fun main() {
    val src = HashSet<Int>()
    src.add(1)
    src.add(12)
    println(src)
    src.toString().substring(1,src.toString().trim().length-1).split(",").forEach {
        println(it.trim().toInt())
    }

}