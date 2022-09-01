//
// Created by Ushop on 2022/8/25.
//
#include "leetcode-utils.hpp"

class Solution {
    int binarySearch(vector<int>& arr, int x,int start,int end){
        if(start >= end){
            return start;
        }
        int mid = (start >>1) + (end >>1);
        if(arr[mid] > x) {
            return binarySearch(arr,x,start,mid);
        }else {
            return binarySearch(arr,x,mid+1,end);
        }
    }
public:
    vector<int> findClosestElements(vector<int>& arr, int k, int x) {
        int index = binarySearch(arr,x,0,(int )arr.size()-1);
        if (k==1){
            if ( arr.size() ==1){
                return arr;
            }else if (arr.size() ==2){
                if(::abs(arr[0]-x) <= ::abs(arr[1]-x)){
                    return {arr[0]};
                }else {
                    return {arr[1]};
                }
             }
        }

        if (index == arr.size()-1){
            index = arr.size() -2;
        }
        deque<int> res;
        int leftPos = index;
        int leftDiff = ::abs(arr[leftPos] - x);
        int rightPos = index + 1;
        int rightDiff = ::abs(arr[rightPos] - x);
        for (int i = 0; i < k; ++i) {
            rightDiff=::abs(arr[rightPos] - x);
            leftDiff = ::abs(arr[leftPos] - x);
            if (leftPos >=0 || rightPos < arr.size()){
                if (leftDiff > rightDiff){
                    res.emplace_back(arr[rightPos++]);
                    if (rightPos >= arr.size()){
                        for (int j = i+1; j < k; ++j) {
                            res.emplace_front(arr[leftPos--]);
                        }
                        break;
                    }
                }else {
                    res.emplace_front(arr[leftPos--]);
                    if (leftPos < 0) {
                        for (int j = i+1; j < k; ++j) {
                            res.emplace_back(arr[rightPos++]);
                        }
                        break;
                    }
                }
            }
        }
//        if (leftPos>0){
//            if (leftDiff <= rightDiff){
//                res.emplace_front(arr[leftPos]);
//                res.pop_back();
//            }
//        }
        vector<int> result;
        result.resize(k);
        for (int i = 0; i < res.size(); ++i) {
            result[i] = res[i];
        }
        return result;
    }
};

int main(){
    Solution solution;
    vector<int> arr{0,0,1,3,5,6,7,8,8};
    auto res = solution.findClosestElements(arr,2,2);  //1 3
    printContainer(res);
    vector<int> arr2{0,0,1,2,3,3,4,7,7,8};
    res = solution.findClosestElements(arr2,3,5); //3 3 4
    printContainer(res);
}