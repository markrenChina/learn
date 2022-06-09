package dsl

class Tr {

    private val children = ArrayList<Td>()

    fun td(block: Td.() -> String) {
        val td = Td()
        td.content = td.block()
        children.add(td)
    }

    fun html():String = StringBuilder().apply {
        append("\n\t<tr>")
        children.forEach {
            this.append(it.html())
        }
        append("\n\t</tr>")
    }.toString()
}