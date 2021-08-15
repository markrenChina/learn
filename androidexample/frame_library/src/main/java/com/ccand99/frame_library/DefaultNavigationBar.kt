package com.ccand99.frame_library

import android.content.Context
import android.view.ViewGroup
import com.ccand99.base_library.navigationbar.AbsNavigationBar

/*
class DefaultNavigationBar(
    private val mParams: AbsNavigationBar.Companion.Builder.Companion.AbsNavigationParams,
    override val layoutId: Int = R.layout.title_bar
) : AbsNavigationBar(mParams, layoutId) {

    override fun applyView() {
        TODO("Not yet implemented")
    }

    companion object {
        */
/**
         * 设置所有效果
         *//*

        class Builder(
            context: Context, parent: ViewGroup
        ) : AbsNavigationBar.Companion.Builder(context, parent) {

            override val p = DefaultNavigationParams(context, parent)

            override fun builder(): AbsNavigationBar<AbsNavigationBar.Companion.Builder.Companion.AbsNavigationParams>
            = AbsNavigationBar<AbsNavigationBar.Companion.Builder.Companion.AbsNavigationParams>(p)

            companion object {
                */
/**
                 * 放置效果
                 *//*

                class DefaultNavigationParams(context: Context, parent: ViewGroup) :
                    AbsNavigationBar.Companion.Builder.Companion.AbsNavigationParams(
                        context, parent
                    )
            }
        }
    }
}*/
