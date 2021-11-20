package context

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.singleWindowApplication
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.ui.window.singleWindowApplication

/*@OptIn(ExperimentalComposeUiApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
fun main() = singleWindowApplication(title = "Context menu") {
    DesktopMaterialTheme { //it is mandatory for Context Menu
        val text = remember {mutableStateOf("Hello!")}
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text(text = "Input") }
        )
    }
}*/

@OptIn( ExperimentalComposeUiApi :: class , androidx.compose.foundation. ExperimentalFoundationApi :: class )
fun  main () = singleWindowApplication(title =  " Context menu " ) {
    DesktopMaterialTheme {// Context Menu
        SelectionContainer {
            Text ( " Hello World! " )
        }
    }
}
