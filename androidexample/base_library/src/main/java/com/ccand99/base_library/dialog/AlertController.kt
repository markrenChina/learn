package com.ccand99.base_library.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.View
import android.view.Window
import androidx.annotation.StyleRes

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

        var mViewSpacingSpecified: Boolean = false

        //布局layout id
        var mViewLayoutResId: Int?= null
        //布局view
        var mView: View?=null

        //dialog按键监听
        var mOnKeyListener: DialogInterface.OnKeyListener?=null

        //dialog消失监听
        var mOnDismissListener: DialogInterface.OnDismissListener?= null

        //dialog取消监听
        var mOnCancelListener: DialogInterface.OnCancelListener?= null

        //点击空白是否取消
        var mCancelable: Boolean = false

        //保存items 文字修改,使布局更通用
        //todo databinding方式
        val mTextMap = SparseArray<CharSequence>()

        /**
         * 绑定和设置参数
         */
        fun apply(mAlert: AlertController){

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