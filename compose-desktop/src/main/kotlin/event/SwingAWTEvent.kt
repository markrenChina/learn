package event

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication {
    var text by remember { mutableStateOf("") }

    Box(
        Modifier.fillMaxSize().pointerInput(Unit) {
            while (true) {
                val event = awaitPointerEventScope { awaitPointerEvent() }
                val awtEvent = event.mouseEvent
                if (event.type == PointerEventType.Press) {
                    text = awtEvent?.locationOnScreen?.toString().orEmpty()
                }
            }
        },
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}
