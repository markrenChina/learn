import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication {
    //painterResource supports raster (BMP, GIF, HEIF, ICO, JPEG, PNG, WBMP, WebP)
    // and vector formats (SVG, XML vector drawable).
    Image(
        painter = painterResource("test.png"),
        contentDescription = "Sample Image",
        modifier = Modifier.fillMaxSize()
    )
}