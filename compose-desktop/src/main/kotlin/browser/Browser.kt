package browser

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import org.eclipse.swt.SWT
import org.eclipse.swt.browser.Browser
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

class Browser {
}

fun main() {
    val display = Display()
    val shell = Shell(display)
    shell.text = "brows"
    shell.setSize(1920,1080)
    val browser = Browser(shell, SWT.FILL)
    browser.setBounds(0,0,1920,1080)
    browser.url = "http://www.baidu.com"
    shell.open()
    while (!shell.isDisposed){
        if (!display.readAndDispatch()){
            display.sleep()
        }
    }
    display.dispose()
}