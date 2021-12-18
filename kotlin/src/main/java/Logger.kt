import java.util.logging.Level
import java.util.logging.Logger

fun main() {
    Logger.getGlobal().info("java global logger")
    Logger.getGlobal().level = Level.OFF
    Logger.getGlobal().info("java global logger")
}

class Logger {
    companion object{
        //静态方式避免未被引用，被垃圾回收
        val myLogger:Logger = Logger.getLogger(Logger::class.java.name)
    }
}