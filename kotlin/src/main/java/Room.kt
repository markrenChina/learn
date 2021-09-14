import java.io.Closeable
import java.io.InputStream
import java.lang.ref.Cleaner

//An autocloseable class using a cleaner as a safety net
class Room(numJunkPiles: Int): AutoCloseable {

    companion object {
        val cleaner: Cleaner = Cleaner.create()

        //State 必须为static 不然会对Room产生引用，阻止垃圾回收
        //Resource that requires cleaning.Must not refer to Room!
        class State(private var numJunkPiles: Int) : Runnable {
            //Number of junk piles in this room
            override fun run() {
                println("Cleaning room")
                numJunkPiles = 0
            }
        }
    }

    private val state: State = State(numJunkPiles)

    private val cleanable = cleaner.register(this,state)

    override fun close() {
        cleanable.clean()
    }

}

class Room2 : Closeable {
    override fun close() {
        //TODO("Not yet implemented")
    }

}

fun main() {
    val room = Room(7)
    val room2 = Room2().use {  }
}