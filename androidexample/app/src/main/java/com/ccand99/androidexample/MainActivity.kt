package com.ccand99.androidexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ccand99.androidexample.databinding.ActivityMainBinding
import com.ccand99.base_library.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initView() {

    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }



}