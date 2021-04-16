#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <android/bitmap.h>
#include <android/log.h>

#define TAG "JNI_TAG"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)

using namespace cv;

//bitmap 转 mat
void bitmap2Mat(JNIEnv *env, Mat &mat, jobject bitmap);

void mat2Bitmap(JNIEnv *env, Mat mat, jobject bitmap);

void bitmap2Mat(JNIEnv *env, Mat &mat, jobject bitmap) {
    //Mat type : CV_8UC4 == Bitmap(ARGB_8888) ,CV_8UC2 == Bitmap(RGB_565)
    AndroidBitmapInfo info;
    //获取Bitmap信息
    AndroidBitmap_getInfo(env, bitmap, &info);
    //像素
    void* pixels;
    //锁定 Bitmap画布(获取头指针)
    AndroidBitmap_lockPixels(env, bitmap, &pixels);
    //创建 mat 的宽高和type BGRA
    mat.create(info.height,info.width,CV_8UC4);

    if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        Mat temp(info.height,info.width,CV_8UC4,pixels);
        //复制数据
        temp.copyTo(mat);
    } else if (info.format == ANDROID_BITMAP_FORMAT_RGB_565) {
        Mat temp(info.height,info.width,CV_8UC2,pixels);
        //CV_8UC2 放入 CV_8UC4
        cvtColor(temp,mat,COLOR_BGR5652BGRA);
    } else {
        //转
    }

    //解锁画布
    AndroidBitmap_unlockPixels(env, bitmap);
}

void mat2Bitmap(JNIEnv *env, Mat mat, jobject bitmap) {
    //mat 转成bitmap
    AndroidBitmapInfo info;
    //获取Bitmap信息
    AndroidBitmap_getInfo(env,bitmap,&info);
    //像素
    void* pixels;
    //锁定 Bitmap画布(获取头指针)
    AndroidBitmap_lockPixels(env,bitmap,&pixels);

    if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        //C4
        Mat temp(info.height,info.width,CV_8UC4,pixels);
        if (mat.type() == CV_8UC4) {
            mat.copyTo(temp);
        } else if (mat.type() == CV_8UC2) {
            cvtColor(mat,temp,COLOR_BGR5652BGRA);
        } else if (mat.type() == CV_8UC1) {
            cvtColor(mat,temp,COLOR_GRAY2BGRA);
        }
    } else if (info.format == ANDROID_BITMAP_FORMAT_RGB_565) {
        //C2
        Mat temp(info.height,info.width,CV_8UC2,pixels);
        if (mat.type() == CV_8UC4) {
            cvtColor(mat,temp,COLOR_BGRA2BGR565);
        } else if (mat.type() == CV_8UC2) {
            mat.copyTo(temp);
        } else if (mat.type() == CV_8UC1) {
            cvtColor(mat,temp,COLOR_GRAY2BGR565);
        }
    } else {
        //转
    }

    //解锁画布
    AndroidBitmap_unlockPixels(env,bitmap);
}
CascadeClassifier cascadeClassifier;
extern "C"
JNIEXPORT int JNICALL
Java_com_ccand99_learnopencv_FaceDetection_saveFaceInfo(JNIEnv *env, jobject thiz, jobject bitmap) {

    //Bitmap 转为C++对象Mat
    Mat mat;
    //从Bitmap获取Mat
    bitmap2Mat(env,mat,bitmap);

    //处理成灰度图，（提高效率）
    Mat gray_mat;
    cvtColor(mat,gray_mat,COLOR_BGRA2GRAY);

    Mat equalize_mat;
    //再次处理 直方均衡补偿
    equalizeHist(gray_mat,equalize_mat);

    //区域
    std::vector<Rect> rect;



    //用分类器识别人脸
    cascadeClassifier.detectMultiScale(equalize_mat,rect,1.1,5);
    LOGE("人脸个数：%d",rect.size());
    if (rect.size() > 0 ){
        for (int i = 0; i < rect.size(); i++) {
            Rect faceRect = rect[i];
            //在人脸部分画个图
            rectangle(mat,faceRect,Scalar(255,0,0),8);
        }
    }
    //人脸信息在equalize_mat里面
    //Mat face_info_mat(equalize_mat,faceRect)
    //放到bitmap里面
    mat2Bitmap(env,mat,bitmap);

    return 0;
}



extern "C"
JNIEXPORT void JNICALL
Java_com_ccand99_learnopencv_FaceDetection_loadCascade(JNIEnv *env, jobject thiz,
                                                       jstring file_path) {
    const char *filePath = env -> GetStringUTFChars(file_path,0);

    cascadeClassifier.load(filePath);

    LOGE("加载分类器文件成功");



}extern "C"
JNIEXPORT jint JNICALL
Java_com_ccand99_learnopencv_FaceDetection_getGrayImage(JNIEnv *env, jobject thiz, jobject bitmap) {
    Mat mat;
    bitmap2Mat(env,mat,bitmap);
    Mat gray_mat;
    cvtColor(mat,gray_mat,COLOR_BGRA2GRAY);
    mat2Bitmap(env,gray_mat,bitmap);
    return 0;
}extern "C"
JNIEXPORT jint JNICALL
Java_com_ccand99_learnopencv_FaceDetection_getEqualizeImage(JNIEnv *env, jobject thiz,
                                                            jobject bitmap) {
    //Bitmap 转为C++对象Mat
    Mat mat;
    //从Bitmap获取Mat
    bitmap2Mat(env,mat,bitmap);
    //处理成灰度图，（提高效率）
    Mat gray_mat;
    cvtColor(mat,gray_mat,COLOR_BGRA2GRAY);
    Mat equalize_mat;
    //再次处理 直方均衡补偿
    equalizeHist(gray_mat,equalize_mat);
    //把灰度mat 放到bitmap里面
    mat2Bitmap(env,equalize_mat,bitmap);
    return 0;
}