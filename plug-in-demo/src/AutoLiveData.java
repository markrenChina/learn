import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import utils.Util;
import view.ShowDialog;

public class AutoLiveData extends AnAction {

    private ShowDialog mDialog;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取project
        Project project = e.getProject();
        //获取光标的位置
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (null == editor){
            return;
        }
        if (mDialog != null && mDialog.isShowing()){
            mDialog.cancelDialog();
        }
        assert project != null;
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        assert psiFile != null;
        //PsiClass psiClass = Util.INSTANCE.getTargetClass(editor, psiFile);
        //assert psiClass != null;
        mDialog = new ShowDialog(editor,project,psiFile);
        mDialog.showDialog();
        //显示一个popup
        //Util.INSTANCE.showPopupBalloon(editor,"Hello world",4);
    }
}
