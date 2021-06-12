package com.ccand99.apt

import androidx.annotation.UiThread

interface Unbinder {
    @UiThread
    fun unbind()

    companion object {
        val EMPTY: Unbinder = object : Unbinder {
            override fun unbind() {}
        }
    }
}