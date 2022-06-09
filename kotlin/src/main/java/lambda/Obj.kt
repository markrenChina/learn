package lambda

class Obj(val i: Int) {

    operator fun plus(obj: Objkt): Obj {
        return Obj(this.i + obj.i)
    }

    override fun toString(): String {
        return "Obj(i=$i)"
    }


}