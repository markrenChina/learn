package com.ccand99.apt

import android.app.Activity
import java.lang.reflect.Constructor

class Apt {

    companion object {
        fun bind(activity: Activity): Unbinder {
            //反射创建实例
            try {
                val bindClassName: Class<out Unbinder> =
                    Class.forName("${activity.javaClass.name}ViewBinding") as Class<out Unbinder>
                //构造函数
                val bindConstructor: Constructor<out Unbinder> =
                    bindClassName.getDeclaredConstructor(activity.javaClass)
                return bindConstructor.newInstance(activity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return Unbinder.EMPTY
        }
    }
}