package com.ccand99.base_library.navigationbar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class AbsNavigationBar<p : AbsNavigationBar.Companion.Builder.Companion.AbsNavigationParams> (
    private val mParams: p,
    override val layoutId: Int
) : INavigationBar {

    /**
     * 创建和绑定view
     */
    val navigationView =
        LayoutInflater.from(mParams.context).inflate(layoutId, mParams.parent, false).also {
            mParams.parent.addView(it,0)
            applyView()
        }

    abstract override fun applyView()

    companion object {
        abstract class Builder(context: Context, parent: ViewGroup) {

            abstract val p: AbsNavigationParams

            abstract fun builder(): AbsNavigationBar<AbsNavigationParams>

            companion object {
                open class AbsNavigationParams(
                    val context: Context,
                    val parent: ViewGroup
                ) {

                }
            }
        }
    }
}