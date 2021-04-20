import java.io.FileInputStream
import java.io.FileOutputStream

class IO {
}

fun main() {
    //println(File("out/TestKt.class").absolutePath)
    FileInputStream("out/production/LearnKotlin/META-INF/explain.mp4").use { input ->
        FileOutputStream("out/production/LearnKotlin/explain.mp4").use { output ->
            //input.copyTo(output)
            val inbuffer = input.buffered()
            val outbuffer = output.buffered()
            inbuffer.copyTo(outbuffer)
        }
    }

}