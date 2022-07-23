//
// Created by Ushop on 2022/7/22.
//
#include "../leetcode-utils.hpp"

/**
当以第i-1个数字结尾地子数组中所有数字地和小于0时，如果把这个负数与第i个数累加，
则得到的结果比第i个数字本身还要小，所以这种情况下以第i个数字结尾的子数组就是第i个数字本身。
如果以第i-1个数字结尾的子数组中所有数字的和大于0，
则与第i个数字累加就得到以第i个数字结尾的子数组中所有数字的和。
*/
class Solution {
public:
    int maxSubArray(vector<int>& nums) {
        int curSum = nums[0];  //f(i)
        int maxSum =  nums[0];  //max[f(i)],默认值等于全部负数情况下输出数组最大值
        for(int i= 1; i < nums.size(); ++i){
            if(curSum <= 0) {
                //f(i-1) <= 0
                curSum = nums[i]; //f(i) = pData[i];
            } else {
                //f(i-1) >0
                curSum = curSum + nums[i];  //f(i-1) + pData[i]
            }
            maxSum = ::max(curSum,maxSum);
        }
        return maxSum;
    }
};

int main(){
    vector<int> vec= {-1,-2};
    Solution solution;
    cout << solution.maxSubArray(vec);
}
