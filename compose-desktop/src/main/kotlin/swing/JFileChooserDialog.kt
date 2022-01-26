package swing

import androidx.compose.runtime.*
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.window.singleWindowApplication
import java.awt.Desktop
import java.awt.Frame
import java.io.File
import javax.swing.JFileChooser
import javax.swing.UIManager


fun main() = singleWindowApplication{
    //val lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel"
    //val lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
    //val lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"
    //val lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel"
    val lookAndFeel = UIManager.getSystemLookAndFeelClassName()
    UIManager.setLookAndFeel(lookAndFeel)
    var isOpen by remember { mutableStateOf(true) }

    if (isOpen) {
        FolderBrowserDialog(
            onCloseRequest = {
                isOpen = false
                println("Result $it")
                it?.let { Desktop.getDesktop().open(File(it)) }
            }
        )
    }
    Thread.sleep(1000)
    println("end")
}

@Composable
private fun FolderBrowserDialog(
    parent: Frame? = null,
    onCloseRequest: (result: String?) -> Unit
) = SwingPanel(
    factory = {
        JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            val res = showOpenDialog(parent)
            if (res == JFileChooser.APPROVE_OPTION){
                onCloseRequest(selectedFile.path)
            }else {
                onCloseRequest(null)
            }
        }
    },
    update = {

    }
)