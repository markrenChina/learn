package meun

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application

@OptIn(ExperimentalComposeUiApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
fun main() = application {
    Tray(
        icon = TrayIcon,
        menu = {
            Item(
                "Exit",
                onClick = this@application::exitApplication
            )
        }
    )
}

