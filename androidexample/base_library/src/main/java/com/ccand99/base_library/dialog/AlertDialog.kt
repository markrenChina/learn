package com.ccand99.base_library.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ListView
import androidx.annotation.StyleRes
import com.ccand99.base_library.R

/**
 * @author markrenChina
 *
 * 自定义dialog
 */
class AlertDialog(
    context: Context,
    @StyleRes themeResId: Int = 0
) : Dialog(context, themeResId) {

    private val mAlert = AlertController(this,window)

    companion object {
        class Builder(
            context: Context,
            @StyleRes private val themeResId: Int = R.style.AlertDialog_AppCompat
        ) {
            private val P = AlertController.AlertParams(context, themeResId)

            /**
             * Set a custom view resource to be the contents of the Dialog. The
             * resource will be inflated, adding all top-level views to the screen.
             * 设置布局ID
             * @param layoutResId Resource ID to be inflated.
             * @return this Builder object to allow for chaining of calls to set
             * methods
             */
            fun setView(layoutResId: Int): Builder {
                P.mView = null
                P.mViewLayoutResId = layoutResId
                P.mViewSpacingSpecified = false
                return this
            }

            //设置元素
            fun setItem(viewId: Int,text : CharSequence): Builder{
                P.mTextMap.put(viewId,text)
                return this
            }

            //设置点击事件
            fun setOnClickListener(viewId: Int,listener: View.OnClickListener): Builder{

                return this
            }

            /**
             * Sets a custom view to be the contents of the alert dialog.
             *
             *
             * When using a pre-Holo theme, if the supplied view is an instance of
             * a [ListView] then the light background will be used.
             *
             *
             * **Note:** To ensure consistent styling, the custom view
             * should be inflated or constructed using the alert dialog's themed
             * context obtained via [.getContext].
             *
             * @param view the view to use as the contents of the alert dialog
             * @return this Builder object to allow for chaining of calls to set
             * methods
             */
            fun setView(view: View): Builder {
                P.mView = view
                P.mViewLayoutResId = 0
                P.mViewSpacingSpecified = false
                return this
            }

            /**
             * Sets whether the dialog is cancelable or not.  Default is true.
             *
             * @return This Builder object to allow for chaining of calls to set methods
             */
            fun setCancelable(cancelable: Boolean): Builder {
                P.mCancelable = cancelable
                return this
            }

            /**
             * Sets the callback that will be called if the dialog is canceled.
             *
             *
             * Even in a cancelable dialog, the dialog may be dismissed for reasons other than
             * being canceled or one of the supplied choices being selected.
             * If you are interested in listening for all cases where the dialog is dismissed
             * and not just when it is canceled, see
             * [ setOnDismissListener][.setOnDismissListener].
             *
             * @return This Builder object to allow for chaining of calls to set methods
             * @see .setCancelable
             * @see .setOnDismissListener
             * @return This Builder object to allow for chaining of calls to set methods
             */
            fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): Builder {
                P.mOnCancelListener = onCancelListener
                return this
            }

            /**
             * Sets the callback that will be called when the dialog is dismissed for any reason.
             *
             * @return This Builder object to allow for chaining of calls to set methods
             */
            fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?): Builder {
                P.mOnDismissListener = onDismissListener
                return this
            }

            /**
             * Sets the callback that will be called if a key is dispatched to the dialog.
             *
             * @return This Builder object to allow for chaining of calls to set methods
             */
            fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener?): Builder {
                P.mOnKeyListener = onKeyListener
                return this
            }

            /**
             * Creates an [AlertDialog] with the arguments supplied to this
             * builder.
             *
             *
             * Calling this method does not display the dialog. If no additional
             * processing is needed, [.show] may be called instead to both
             * create and display the dialog.
             */
            fun create(): AlertDialog {
                // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
                // so we always have to re-set the theme
                val dialog = AlertDialog(P.mContext, themeResId)
                P.apply(dialog.mAlert)
                dialog.setCancelable(P.mCancelable)
                if (P.mCancelable) {
                    dialog.setCanceledOnTouchOutside(true)
                }
                dialog.setOnCancelListener(P.mOnCancelListener)
                dialog.setOnDismissListener(P.mOnDismissListener)
                if (P.mOnKeyListener != null) {
                    dialog.setOnKeyListener(P.mOnKeyListener)
                }
                return dialog
            }

            /**
             * Creates an [AlertDialog] with the arguments supplied to this
             * builder and immediately displays the dialog.
             *
             *
             * Calling this method is functionally identical to:
             * <pre>
             * AlertDialog dialog = builder.create();
             * dialog.show();
            </pre> *
             */
            fun show(): AlertDialog {
                val dialog = create()
                dialog.show()
                return dialog
            }
        }
    }

}