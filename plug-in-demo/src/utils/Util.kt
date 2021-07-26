package utils

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.SyntheticElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.JBColor
import java.awt.Color


object Util {

    /**
     * 显示dialog
     *
     * @param editor
     * @param result 内容
     * @param time   显示时间，单位秒
     */
    fun showPopupBalloon(editor: Editor, result: String, time: Int) {
        ApplicationManager.getApplication().invokeLater {
            val factory = JBPopupFactory.getInstance()
            factory.createHtmlTextBalloonBuilder(
                result,
                null,
                JBColor(Color(116, 214, 238), Color(76, 112, 117)),
                null
            )
                .setFadeoutTime((time * 1000).toLong())
                .createBalloon()
                .show(factory.guessBestPopupLocation(editor), Balloon.Position.below)
        }
    }

    /**
     * 根据当前文件获取对应的class文件
     *
     * @param editor
     * @param file
     * @return
     */
    fun getTargetClass(editor: Editor, file: PsiFile): PsiClass? {
        val offset = editor.caretModel.offset
        val element = file.findElementAt(offset)
        return if (element == null) {
            null
        } else {
            val target = PsiTreeUtil.getParentOfType(element, PsiClass::class.java)
            if (target is SyntheticElement) null else target
        }
    }
}