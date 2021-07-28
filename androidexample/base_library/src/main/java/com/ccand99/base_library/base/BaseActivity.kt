package com.ccand99.base_library.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<DB: ViewDataBinding>: AppCompatActivity() {

    //设置布局id
    @get: LayoutRes
    abstract val layoutId: Int

    protected lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,layoutId)
        //初始化livedata
        initViewModel()
        //初始化界面
        initView()
        //初始化数据
        initData()
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun initViewModel()



}