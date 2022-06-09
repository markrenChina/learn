package demo

import kotlin.reflect.KProperty

class Status {
    var mValue : Int = 0

    operator fun<T> getValue(myClass: MySet<T>,prop: KProperty<*>) : Any? {
        return mValue
    }

    operator fun<T> setValue(myClass: MySet<T>,prop: KProperty<*>,value: Any?) {
        mValue = value as Int
    }
}