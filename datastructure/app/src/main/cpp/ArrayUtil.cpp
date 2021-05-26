//
// Created by mark on 2021/5/26.
//
#include <stdlib.h>

namespace c9 {
    int *create_random_array(int len, int low, int high) {
        int *arr = new int[len];

        for (int i = 0; i < len; ++i) {
            arr[i] = rand() % (high - low) + low;
        }

        return arr;
    }
}