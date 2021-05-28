package com.ccand99.learnopencv

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ccand99.learnopencv.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bitmap: Bitmap
    private var faceDetection: FaceDetection = FaceDetection()
    private lateinit var mCascadeFile: File
    private var currentIndex = 0
    private val images = listOf<Int>(
        R.drawable.poto0,
        R.drawable.poto1,
        R.drawable.poto2,
        R.drawable.poto3,
        R.drawable.poto4,
        R.drawable.poto5,
        R.drawable.poto6,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.poto0)
        binding.baseImage.setImageBitmap(bitmap)
        if (copyCascadeFile()) {
            val faceDetection = FaceDetection()
            faceDetection.loadCascade(mCascadeFile.absolutePath)
        } else {
            Toast.makeText(this,"加载分类器文件失败",Toast.LENGTH_LONG).show()
        }
        faceDetection.sg()
    }

    private fun copyCascadeFile(): Boolean {
        try {
            val cascadeDir: File = getDir("cascade", Context.MODE_PRIVATE)
            mCascadeFile = File(cascadeDir, "lbpcascade_frontalface.xml")
            if (mCascadeFile.exists()) return true
            resources.openRawResource(R.raw.lbpcascade_frontalface).use { input ->
                FileOutputStream(mCascadeFile).use { fileout ->
                    input.copyTo(fileout)
                }
            }
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("main", "Failed to load cascade. Exception thrown: $e")
        }
        return false
    }

    fun recognition(view: View) {
        faceDetection.saveFaceInfo(bitmap)
        binding.baseImage.setImageBitmap(bitmap)
    }

    fun getGrayImages(view: View) {
        val grayBitmap = BitmapFactory.decodeResource(resources, images[currentIndex])
        faceDetection.getGrayImage(grayBitmap)
        binding.grayImage.setImageBitmap(grayBitmap)
    }

    fun getEqualizeImages(view: View) {
        val equalizeBitmap = BitmapFactory.decodeResource(resources, images[currentIndex])
        faceDetection.getGrayImage(equalizeBitmap)
        binding.equalizeImage.setImageBitmap(equalizeBitmap)
    }

    fun changeImage(view: View) {
        currentIndex++
        bitmap = BitmapFactory.decodeResource(resources, images[currentIndex])
        binding.baseImage.setImageBitmap(bitmap)
    }
}