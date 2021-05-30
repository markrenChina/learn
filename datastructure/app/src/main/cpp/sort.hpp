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
     * 冒泡排序 从小到大 优化前
     * @param arr
     * @param len
     */
    void bubbleSort(int arr[], int len);

    /**
     * 冒泡排序 从小到大 优化后
     * @param arr
     * @param len
     */
    void optimizeBubbleSort(int arr[], int len);

    /*
     * 选择排序 从小到大
     */
    void selectSort(int arr[], int len);

    /**
     * 插入排序
     * @param arr
     * @param len
     */
    void insertSort(int arr[], int len);

    /**
     * 希尔排序
     * @param arr
     * @param len
     */
    void shellInsertSort(int arr[], int len);

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

void c9::optimizeBubbleSort(int *arr, int len) {
    // 记录上一次最后的位置
    int last = len - 1;
    int temp = 0;
    do {
        temp = 0;
        for (int i = 0; i < last; ++i) {
            if (arr[i] > arr[i + 1]) {
                std::swap(arr[i + 1], arr[i]);
                temp = i;
            }
        }
        last = temp;
    } while (last > 0);
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

void c9::insertSort(int arr[], int len) {
    //优化前
    /*for (int i = 1; i < len; i++) {
        for (int j = i; j > 0; --j) {
            if (arr[j] < arr[j - 1]) {
                std::swap(arr[j-1], arr[j]);
            } else {
                break;
            }
        }
    }*/

    int j;
    for (int i = 1; i < len; i++) {
        //用临时变量减少swap中的三次交换次数
        int temp = arr[i];
        for (j = i; j > 0 && arr[j - 1] > temp; --j) {
            arr[j] = arr[j - 1];
        }
        arr[j] = temp;
    }
}

void c9::shellInsertSort(int *arr, int len) {
    //分组 步长
    int increment = len / 2;
    int i, j, k;
    while (increment > 0) {
        //希尔排序
        for (i = 0; i < increment; ++i) {
            for (j = i + increment; j < len; j += increment) {
                int temp = arr[j];
                for (k = j; k > i && arr[k - increment] > temp; k -= increment) {
                    //std::swap(arr[k],arr[k - increment]);
                    arr[k] = arr[k - increment];
                }
                arr[k] = temp;
            }
        }
        increment /= 2;
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
