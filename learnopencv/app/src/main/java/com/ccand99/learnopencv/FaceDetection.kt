package com.ccand99.learnopencv

import android.graphics.Bitmap

class FaceDetection(
    val age: Int?= null
) {
    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }


    external fun saveFaceInfo(bitmap: Bitmap): Int

    external fun getGrayImage(bitmap: Bitmap): Int

    external fun getEqualizeImage(bitmap: Bitmap): Int

    /**
     * 加载人脸识别分类器文件
     */
    external fun loadCascade(filePath: String)
}
