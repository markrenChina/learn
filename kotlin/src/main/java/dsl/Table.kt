package dsl

fun table(block: Table.() -> Unit): String {
    val table = Table()
    table.block()
    return table.html()
}

class Table {

    private val children = ArrayList<Tr>()

    fun tr(block: Tr.() -> Unit) {
        val tr = Tr()
        tr.block()
        children.add(tr)
    }

    fun html():String  = with(StringBuilder()){
        append("<table>")
        children.forEach {
            append(it.html())
        }
        append("\n</table>")
    } .toString()
}

fun main(){
    val html = table {
        tr {
            td { "Apple" }
            td { "Grape" }
            td { "Orange" }
        }
        tr {
            td { "Pear" }
            td { "Banana" }
            td { "Watermelon" }
        }
    }
    println(html)
}