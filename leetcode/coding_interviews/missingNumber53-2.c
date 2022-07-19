//
// Created by Ushop on 2022/7/18.
//
#include <stdio.h>

//基于二分查找法寻找第一个角标不等于值的值
int missingNumber(int* nums, int numsSize){
    if(nums == NULL || numsSize <= 0){
        return -1;
    }
    int left = 0;
    int right = numsSize -1;
    while (left <= right){
        int middle = (right + left) >> 1;
        if(nums[middle] != middle){
            if(middle == 0 || nums[middle - 1] ==middle -1){
                return middle;
            }
            right= middle -1;
        } else {
            left = middle + 1;
        }
    }
    if(left == numsSize){
        return left;
    }
    return -1;
}

int main(){
    int nums[3] = {1,2,3};
    printf("%d", missingNumber(nums,3));
}