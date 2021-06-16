#include <jni.h>
#include <string>
#include <android/log.h>
#include "LinkedList.hpp"
#include "sort.hpp"
#include "ArrayUtil.cpp"
#include "ArrayStack.hpp"
#include "ArrayQueue.hpp"
#include "binaryTree.hpp"
#include "PriorityQueue.hpp"

#define TAG "JNI_TAG"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)


int* copy_arr(int* src,int len){
    int *new_arr= new int[len];
    memcpy(new_arr,src,sizeof(int)*len);
    return new_arr;
}

void print_array(int* arr,int len){
    for (int i = 0; i< len; ++i){
        LOGI("%d",arr[i]);
    }
}

void test_LinkedList() {
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
}

void test_sort() {
    // 测试排序算法
    int len = 200000;
    int* arr = c9::create_random_array(len,0,2000000);
    int* arr2 = copy_arr(arr,len);
    int* arr3 = copy_arr(arr,len);
    //c9::bubbleSort(arr,len);
    //c9::selectSort(arr,len);
    //c9::sort_array("bubbleSort",c9::bubbleSort,arr,len);

    //c9::sort_array("selectSort",c9::selectSort,arr2,len);

    //c9::optimizeBubbleSort(arr,len);
    //c9::sort_array("optimizeBubbleSort",c9::optimizeBubbleSort,arr3,len);
    //c9::insertSort(arr,len);
    //c9::sort_array("insertSort",c9::insertSort,arr,len);
    //c9::shellInsertSort(arr,len);
    //c9::sort_array("shellInsertSort",c9::shellInsertSort,arr3,len);
    //c9::mergeSort(arr,len);
    c9::sort_array("mergeSort",c9::mergeSort,arr2,len);
    //c9::quickSort(arr,len);
    //c9::sort_array("quickSort",c9::quickSort,arr,len);
    //c9::quickSort3ways(arr,len);
    c9::sort_array("quickSort3ways",c9::quickSort3ways,arr3,len);
    //c9::headSort(arr,len);
    c9::sort_array("headSort",c9::headSort,arr,len);

    //print_array(arr,len);
    delete[] (arr);
    delete[] (arr2);
    delete[] (arr3);
}

void test_stack() {
    using namespace c9;

    ArrayStack<int> stack;

    for (int i=0;i<10;++i) {
        stack.push(i);
    }

    while (!stack.isEmpty()) {
        LOGI("%d",stack.pop());
    }

}

void test_arrayQueue(){
    c9::ArrayQueue<int> queue;

    queue.push(1);
    queue.push(2);
    queue.push(3);
    queue.push(4);

    for (int i = 0 ; i< 30 ; i++){
        queue.push(i);
    }

    while (!queue.isEmpty()){
        LOGI("%d",queue.pop());
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_ccand99_datastructure_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {

    //test_LinkedList();
    test_sort();

    //test_stack();
    //test_arrayQueue();
    //c9::test_binaryTree();
    //c9::test_priorityQueue();

    __android_log_print(ANDROID_LOG_INFO,"TAG","jni end");
    //android_

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}