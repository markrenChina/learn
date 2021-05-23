#include <jni.h>
#include <string>
#include <android/log.h>
#include "LinkedList.hpp"
#include "sort.hpp"

extern "C" JNIEXPORT jstring JNICALL
Java_com_ccand99_datastructure_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {

    //测试
    LinkedList<int> *linkedList = new LinkedList<int>;

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
        __android_log_print(ANDROID_LOG_INFO,"TAG","%d",linkedList->get(i));
    }

    delete linkedList;

    __android_log_print(ANDROID_LOG_INFO,"TAG","end");
    //android_

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}