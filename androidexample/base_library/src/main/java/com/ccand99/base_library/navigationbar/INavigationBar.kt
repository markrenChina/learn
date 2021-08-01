package com.ccand99.base_library.navigationbar

import androidx.annotation.LayoutRes

interface INavigationBar {
    /**
     * 头部的规范
     */
    //设置布局id
    @get: LayoutRes
    abstract val layoutId: Int

    /**
     * 绑定参数
     */
    fun applyView()
}