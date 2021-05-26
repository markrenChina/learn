#include <jni.h>
#include <string>
#include <android/log.h>
#include "LinkedList.hpp"
#include "sort.hpp"
#include "ArrayUtil.cpp"

#define TAG "JNI_TAG"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)

void print_array(int* arr,int len){
    for (int i = 0; i< len; ++i){
        LOGI("%d",arr[i]);
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_ccand99_datastructure_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {

    //测试
    auto *linkedList = new LinkedList<int>;

    //index 2
    linkedList->push(2);
    //index 1
    linkedList->push(1);
    //index 0
    linkedList->push(0);
    //index 3
    linkedList->add(3);
    //index 4
    linkedList->add(5);
    linkedList->insert(4,4);
    linkedList->remove(1);

    for (int i = 0; i < linkedList->getSize();i++){
        //__android_log_print(ANDROID_LOG_INFO,"TAG","%d",linkedList->get(i));
    }

    delete linkedList;

    // 测试排序算法
    int len = 6;
    int* arr = c9::create_random_array(len,20,100);
    //c9::bubbleSort(arr,len);
    c9::selectSort(arr,len);
    print_array(arr,len);

    delete[] (arr);

    __android_log_print(ANDROID_LOG_INFO,"TAG","jni end");
    //android_

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}