package com.ccand99.base_library.dialog

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.lang.ref.WeakReference

/**
 * @author markrenChina
 *
 * dialog的辅助处理类
 */
internal class DialogViewHelper(val dialogView: View) {

    private val mViews = SparseArray<WeakReference<View>>()

    /**
     * 次构造函数
     */
    constructor(context: Context, layoutResId: Int) : this(
        LayoutInflater.from(context).inflate(layoutResId, null)
    )

    /**
     * 设置文本
     */
    fun setText(viewId: Int, text: CharSequence) {
        val tv: TextView? =
            (mViews.get(viewId)?.get() as? TextView) ?: dialogView.findViewById<TextView>(viewId)
                ?.also { mViews.put(viewId, WeakReference(it)) }
        tv?.let { it.text = text }
    }

    /**
     * 设置点击
     */
    fun setOnClickListen(viewId: Int, listener: View.OnClickListener) {
        val view = mViews.get(viewId)?.get() ?: dialogView.findViewById<View>(viewId)
            ?.also { mViews.put(viewId, WeakReference(it)) }
        view?.let { it.setOnClickListener(listener) }
    }

    /**
     * 获取view
     */
    fun <T : View> getView(viewId: Int) : T? =
        (mViews.get(viewId)?.get() ?: dialogView.findViewById<View>(viewId))?.let { it as? T }
}