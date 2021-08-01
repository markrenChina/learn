package com.ccand99.androidexample

import android.view.View
import android.widget.Toast
import com.ccand99.androidexample.databinding.ActivityMainBinding
import com.ccand99.base_library.base.BaseActivity
import com.ccand99.base_library.dialog.AlertDialog
import com.ccand99.frame_library.DefaultNavigationBar

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initData() {
        //TODO("Not yet implemented")
    }

    override fun initView() {
        val navigationBar = DefaultNavigationBar.Companion.Builder( this,findViewById(R.id.nav_main)).builder()
    }

    override fun initViewModel() {
        //TODO("Not yet implemented")
    }

    fun openDialog(v: View){
        val dialog = AlertDialog.Companion.Builder(this)
            .setView(R.layout.dialog_test)
            .setOnClickListener(R.id.dialog_bt_ok,View.OnClickListener {
                Toast.makeText(this,"测试",Toast.LENGTH_LONG).show()
            })
            .setText(R.id.dialog_bt_ok,"确定")
            .fromBottom(true)
            .show()
    }
}