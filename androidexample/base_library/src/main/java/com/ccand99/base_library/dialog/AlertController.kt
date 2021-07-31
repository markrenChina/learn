package com.ccand99.base_library.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StyleRes
import androidx.core.util.forEach
import java.lang.IllegalArgumentException

/**
 * @author markrenChina
 */
internal class AlertController(
    private val mDialog: AlertDialog,
    private val mWindow: Window?
) {

    class AlertParams(
        val mContext: Context,
        @StyleRes val themeResId: Int
    ) {



        //位置
        var mGravity: Int = Gravity.CENTER
        //动画
        var mAnimations: Int = 0
        //布局宽度
        var mWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT
        var mHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT

        //点击空白取消
        var mViewSpacingSpecified: Boolean = true

        //布局layout id
        var mViewLayoutResId: Int? = null

        //布局view
        var mView: View? = null

        //dialog按键监听
        var mOnKeyListener: DialogInterface.OnKeyListener? = null

        //dialog消失监听
        var mOnDismissListener: DialogInterface.OnDismissListener? = null

        //dialog取消监听
        var mOnCancelListener: DialogInterface.OnCancelListener? = null

        //点击空白是否取消
        var mCancelable: Boolean = false

        //保存items 文字修改,使布局更通用
        //todo databinding方式
        val mTextMap = SparseArray<CharSequence>()

        //存放点击事件
        val mClickArray = SparseArray<View.OnClickListener>()

        /**
         * 绑定和设置参数
         */
        fun apply(mAlert: AlertController) {
            //设置参数

            //设置布局
            val viewHelper: DialogViewHelper = if (mViewLayoutResId != 0) {
                DialogViewHelper(mContext, mViewLayoutResId!!)
            } else if (mView != null) {
                DialogViewHelper(mView!!)
            } else {
                throw IllegalArgumentException("请使用setView设置布局")
            }
            mAlert.mDialog.setContentView(viewHelper.dialogView)

            //设置文本
            mTextMap.forEach { key, value ->
                viewHelper.setText(key,value)
            }
            //设置点击
            mClickArray.forEach { key, value ->
                viewHelper.setOnClickListen(key,value)
            }

            //设置自定义
            mAlert.mWindow?.also {
                it.setGravity(mGravity)
                if (mAnimations != 0){
                    it.setWindowAnimations(mAnimations)
                }
                val params = it.attributes
                params.width = mWidth
                params.height = mHeight
                it.attributes = params
            }
        }

        /**
         * Set the view to display in the dialog along with the spacing around that view
         */
        fun setView(
            view: View, viewSpacingLeft: Int, viewSpacingTop: Int, viewSpacingRight: Int,
            viewSpacingBottom: Int
        ) {
            mView = view
            mViewLayoutResId = 0
            mViewSpacingSpecified = true
            //mViewSpacingLeft = viewSpacingLeft
            //mViewSpacingTop = viewSpacingTop
            //mViewSpacingRight = viewSpacingRight
            //mViewSpacingBottom = viewSpacingBottom
        }
    }
}