package view


import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import utils.CANCEL
import utils.OK
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder


class ShowDialog(
    private val mEditor: Editor,
    private val mProject: Project,
    // 获取当前文件
    private val mPsiFile: PsiFile,
    // class
    //private val mPsiClass: PsiClass
) :JFrame(),ActionListener {

    // 标签JPanel
    private val mPanelTitle = JPanel()
    private val mTitleFields = JLabel("Fields")
    private val mTitleTypes = JLabel("Types")

    init {
        //java GUI
        initTopPanel()
    }

    private fun initTopPanel() {
        mPanelTitle.layout = GridLayout(1, 4, 10, 10)
        mPanelTitle.border = EmptyBorder(5, 10, 5, 10)
        mTitleFields.horizontalAlignment = JLabel.LEFT
        mTitleTypes.horizontalAlignment = JLabel.LEFT
        // 添加到JPanel
        mPanelTitle.add(mTitleFields)
        mPanelTitle.add(mTitleTypes)
        mPanelTitle.setSize(720, 30)
        // 添加到JFrame
        contentPane.add(mPanelTitle, 0)
    }

    override fun actionPerformed(e: ActionEvent) {
        when(e.actionCommand){
            OK -> {
                //取消dialog，写入属性
                cancelDialog()
            }
            CANCEL -> {
                //取消dialog
                cancelDialog()
            }
        }
    }

    /**
     * 显示dialog
     */
    fun showDialog() {
        // 显示
        isVisible = true
    }

    /**
     * 关闭dialog
     */
    fun cancelDialog() {
        isVisible = false
        dispose()
    }
}