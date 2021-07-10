package com.ccand99.aspectjxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvHello : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvHello = findViewById(R.id.hello)
    }


    //@Toast2
    @Toast("点击")
    fun setTvHello(v:View) {
        tvHello.text = "markrenChina"
    }
}