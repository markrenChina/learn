//
// Created by Ushop on 2022/8/12.
//
#include "../leetcode-utils.hpp"


//先把数组分隔成子数组，统计出子数组内部的逆序对的数目，然后再统计出两个相邻子数组之间的逆序对数目
//在统计逆序对的过程中，还需要对数组进行排序。（归并排序）
class Solution {
private:
    int _reversePairs(vector<int>& nums ,int start,int end,vector<int>& tmp){
        if (start == end){
            return 0;
        }
        int mid = start + ((end - start)>> 1);
        int rightStart = mid+1;
        //归
        int left = _reversePairs(nums,start,mid,tmp);
        int right = _reversePairs(nums,rightStart,end,tmp);
        //并
        //mid 指向left的最后,start指向left的最前
        //end 指向right的最后,mid+1指向right的最前
        if (nums[mid] < nums[rightStart]){
            return left+right;
        }
        int sum = 0;
        int rightEnd = end;
        int tmpPos = end;
        while (mid >= start && end >= rightStart){
            if (nums[mid] > nums[end]){
                sum += (end - rightStart + 1);
                tmp[tmpPos] = nums[mid];
                --tmpPos;
                --mid;
            }else {
                tmp[tmpPos] = nums[end];
                --end;
                --tmpPos;
            }
        }
        while (end >= rightStart){
            tmp[tmpPos] = nums[end];
            --end;
            --tmpPos;
        }
        ++tmpPos;
        for (; tmpPos <=rightEnd; ++tmpPos) {
            nums[tmpPos] = tmp[tmpPos];
        }
        return sum+left+right;
    }

public:
    int reversePairs(vector<int>& nums) {
        if(nums.empty()){
            return 0;
        }
        vector<int> tmp;
        tmp.resize(nums.size());
        return _reversePairs(nums,0,(int)nums.size()-1,tmp);
    }
};

int main(){
    Solution solution;
//    vector<int> vec{7,5,6,4};
    vector<int> vec{233,2000000001,234,2000000006,235,2000000003,236,2000000007,237,2000000002,2000000005,233,233,233,233,233,2000000004};
    cout << solution.reversePairs(vec);
}