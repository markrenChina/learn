package event

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.mouseClickable
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isAltPressed
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.isMetaPressed
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.isShiftPressed
import androidx.compose.ui.input.pointer.isTertiaryPressed
import androidx.compose.ui.window.singleWindowApplication

@OptIn(ExperimentalFoundationApi::class)
fun main() = singleWindowApplication {
    var clickableText by remember { mutableStateOf("Click me!") }

    Text(
        modifier = Modifier.mouseClickable(
            onClick = {
                clickableText = buildString {
                    append("Buttons pressed:\n")
                    append("primary: ${buttons.isPrimaryPressed}\t")
                    append("secondary: ${buttons.isSecondaryPressed}\t")
                    append("tertiary: ${buttons.isTertiaryPressed}\t")

                    append("\n\nKeyboard modifiers pressed:\n")

                    append("alt: ${keyboardModifiers.isAltPressed}\t")
                    append("ctrl: ${keyboardModifiers.isCtrlPressed}\t")
                    append("meta: ${keyboardModifiers.isMetaPressed}\t")
                    append("shift: ${keyboardModifiers.isShiftPressed}\t")
                }
            }
        ),
        text = clickableText
    )
}
