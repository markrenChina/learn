package windows

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * This function should be used in a Composable scope.
 * The easiest way to create a Composable scope is to use the application function:
 */
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        // Content
    }
}
