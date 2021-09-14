import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

inline fun<reified T> File.jsonRead(): T?{
    if (!this.exists() || this.isDirectory) {
        return null
    }
    JsonReader(this.bufferedReader()).use { jsonReader ->
        return Gson().fromJson(jsonReader,T::class.java)
    }
}

inline fun <reified T> File.jsonWrite(obj: T) {
    this.writeText(Gson().toJson(obj))
}

inline fun <reified T> File.asyncJsonWrite(obj: T) {
    CoroutineScope(context = Dispatchers.IO).launch{
        this@asyncJsonWrite.writeText(Gson().toJson(obj))
    }.start()
}