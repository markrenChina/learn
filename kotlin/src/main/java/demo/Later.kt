package demo

import kotlin.reflect.KProperty

class Later<T>(val block: () -> T) {

    var value: Any? = null

    operator fun getValue(any: Any?,prop: KProperty<*>) : T {
        value ?: kotlin.run { value = block() }
        return value as T
    }
}