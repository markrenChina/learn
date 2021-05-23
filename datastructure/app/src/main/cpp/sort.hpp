//
// Created by mark on 2021/5/23.
//

#ifndef DATASTRUCTURE_SORT_HPP
#define DATASTRUCTURE_SORT_HPP

#include <jni.h>

namespace c9{
    /**
     * 冒泡排序 从小到大
     * @param arr
     * @param len
     */
    void bubbleSort(int arr[],int len);
}

void c9::bubbleSort(int *arr, int len) {
    for (int i= 0 ;i <len -1; ++i){
        for (int j=0;j<len -1 -i; ++j){
            if (arr[j] > arr[j+1]) {
                std::swap(arr[j],arr[j+1]);
            }
        }
    }
}

/*int min ;
for (int i = 0 ;i < len-1 ; i++) {
min = arr[i];
for (int j = i+1 ; j < len ; j++){
if (min < arr[j]){
int  temp = min;
min = arr[j];
arr[j] = temp;
}
}
if (min == arr[i]){
break;
} else {
arr[i] = min;
}
}*/

#endif //DATASTRUCTURE_SORT_HPP
