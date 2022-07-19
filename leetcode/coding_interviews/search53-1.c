//
// Created by Ushop on 2022/7/18.
//
//C自己实现，利用二分查找发寻找起始角标，和终点角标。模拟c++的equal_range函数
#include <stdio.h>

int GetFirstK(int* data,int length,int k,int start,int end){
    if(start > end){
        return -1;
    }
    int middleIndex = start + ((end-start) >> 1);
    int middleData = data[middleIndex];
    if(middleData > k){
        end = middleIndex - 1;
    }else if(middleData < k){
        start = middleIndex + 1;
    }else {
        //=0已经是第一个了或前一个不是目标元素，已经找到
        if((middleIndex==0)||(data[middleIndex - 1 ]!= k)){
            return middleIndex;
        } else {
            end = middleIndex - 1;
        }
    }
    return GetFirstK(data,length,k,start,end);
}

int GetLastK(int* data,int length,int k, int start,int end){
    if(start > end){
        return -1;
    }
    int middleIndex = start + ((end-start) >> 1);
    int middleData = data[middleIndex];
    if(middleData > k){
        end = middleIndex - 1;
    }else if(middleData < k){
        start = middleIndex + 1;
    }else {
        if((middleIndex == length -1) || (data[middleIndex +1] != k)){
            return middleIndex;
        } else {
            start = middleIndex + 1;
        }
    }
    return GetLastK(data,length,k,start,end);
}


int search(int* nums, int numsSize, int target){
    if(numsSize <= 0){
        return 0;
    }
    int start = GetFirstK(nums,numsSize,target,0,numsSize - 1);
    if(start == -1){
        return 0;
    }
    int end = GetLastK(nums,numsSize,target,0,numsSize -1);
//    int end = GetLastK(nums,numsSize,target,start,numsSize -1);
    return end-start + 1;
}

int main(int argc,char** args ){
    int nums[6] = {5,7,7,8,8,10};
    printf("%d", search(nums,6,8));
}