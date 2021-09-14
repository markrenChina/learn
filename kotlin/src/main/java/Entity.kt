class Entity {
    var name = ""
}

fun main() {
    val entity = Entity()
    entity.name = "bbcA"
    println(entity.name)
    entity.name = " b"
    println(entity.name)
    entity.name = "a13"
    println(entity.name)
}

fun trimSpace(field: String,value: String){
    //field = value.trim { it <= ' ' }
}
