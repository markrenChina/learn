package com.ccand99.androidexample

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.ccand99.androidexample.databinding.ActivityMainBinding
import com.ccand99.base_library.base.BaseActivity
import com.ccand99.base_library.dialog.AlertDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.URL

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initData() {
        Observable.just("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.itread01.com%2Fuploads%2Fimages%2F20161102%2F1478019030-4658.jpg&refer=http%3A%2F%2Fwww.itread01.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631525033&t=7246db12b6da496250701f4e6cb6803c")
            .map {
                URL(it).openConnection().getInputStream().use { ins->
                    return@map BitmapFactory.decodeStream(ins)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.iamge.setImageBitmap(it)
            }
            //.subscribe(object : Observable)

    }

    override fun initView() {
        //val navigationBar = DefaultNavigationBar.Companion.Builder( this,findViewById(R.id.nav_main)).builder()
    }

    override fun initViewModel() {
        //TODO("Not yet implemented")
    }

    fun openDialog(v: View) {
        val dialog = AlertDialog.Companion.Builder(this)
            .setView(R.layout.dialog_test)
            .setOnClickListener(R.id.dialog_bt_ok, View.OnClickListener {
                Toast.makeText(this, "测试", Toast.LENGTH_LONG).show()
            })
            .setText(R.id.dialog_bt_ok, "确定")
            .fromBottom(true)
            .show()
    }
}