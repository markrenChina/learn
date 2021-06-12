package com.ccand99.apt

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ccand99.apt_annotations.BindView

class MainActivity : AppCompatActivity() {

    @BindView(R.id.hello_world)
    var helloWorld: TextView? = null

    private lateinit var mUnbinder: Unbinder

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mUnbinder = Apt.bind(this)
        helloWorld?.text = "hello markrenChina!!"
    }

    override fun onDestroy() {
        mUnbinder.unbind()
        super.onDestroy()
    }
}