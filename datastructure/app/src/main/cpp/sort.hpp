//
// Created by mark on 2021/5/23.
//

#ifndef DATASTRUCTURE_SORT_HPP
#define DATASTRUCTURE_SORT_HPP

#include <jni.h>
#include <time.h>
#include <assert.h>
#include <android/log.h>

#define TAG "JNI_TAG"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)

namespace c9 {
    /**
     * 冒泡排序 从小到大
     * @param arr
     * @param len
     */
    void bubbleSort(int arr[], int len);

    /*
     * 选择排序 从小到大
     */
    void selectSort(int arr[], int len);

    /**
     * 算法测试方法
     */
    void sort_array(char *sortName, void (*sort)(int *, int), int *arr, int len);
}

void c9::bubbleSort(int *arr, int len) {
    for (int i = 0; i < len - 1; ++i) {
        bool swaped = false;
        for (int j = 0; j < len - 1 - i; ++j) {
            if (arr[j] > arr[j + 1]) {
                swaped = true;
                std::swap(arr[j], arr[j + 1]);
            }
        }
        if (!swaped) {
            break;
        }
    }
}

void c9::selectSort(int *arr, int len) {
    for (int i = 0; i < len; ++i) {
        int min = i;
        for (int j = i + 1; j < len; ++j) {
            if (arr[min] > arr[j]) {
                min = j;
            }
        }
        if (min != i) {
            std::swap(arr[min], arr[i]);
        }
    }
}

void c9::sort_array(char *sortName, void (*sort)(int *, int), int *arr, int len) {
    {
        size_t start_time = clock();
        sort(arr, len);
        size_t end_time = clock();
        double time = (double) (end_time - start_time) / CLOCKS_PER_SEC;
        LOGD("%s的执行时间%lf", sortName, time);
        //检查数组是否排序
        for (int i = 0; i < len - 1; ++i) {
            assert(arr[i] <= arr[i + 1]);
        }
    }
}


#endif //DATASTRUCTURE_SORT_HPP
