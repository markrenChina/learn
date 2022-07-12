//
// Created by mark on 2022/7/9.
//
#include "sort.hpp"
#include "PriorityQueue.hpp"


namespace c9 {
    int *create_random_array(int len, int low, int high) {
        int *arr = new int[len];

        for (int i = 0; i < len; ++i) {
            arr[i] = rand() % (high - low) + low;
        }

        return arr;
    }

    int* copy_arr(int* src,int len){
        int *new_arr= new int[len];
        memcpy(new_arr,src,sizeof(int)*len);
        return new_arr;
    }
}

int main(){
    // 测试排序算法
//    int len = 200000;
//    int* arr = c9::create_random_array(len,0,len + (len>>1));
//    //c9::sort_array("headSort",c9::headSort,arr,len);
//    c9::sort_array("insertSort",c9::insertSort,arr,len);
//    //std::sort()
//    delete[] (arr);
    int len = 20;
    int* arr = c9::create_random_array(len,0,len + (len>>1));
    int* arr2 = c9::copy_arr(arr,len);
    std::cout << "src : ";
    for (int i = 0; i < len; ++i) {
        std::cout << arr[i] << " ";
    }
    std::cout << std::endl;
    c9::test_priorityQueue2(arr,len);
    c9::sort_array("headSort",c9::headSort,arr2,len);
    //c9::PriorityQueue<int > pQueue(arr,len);
}