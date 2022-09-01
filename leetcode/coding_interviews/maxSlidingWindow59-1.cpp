//
// Created by Ushop on 2022/8/9.
//
#include "../leetcode-utils.hpp"

//需要大根堆方便移除数据的数据结构
class Solution {
private:
    int findMax(vector<int> window,int& maxValue){
        int max = window[0];
        int maxPos = 0;
        for (int i = 1; i < window.size(); ++i) {
            if (max <= window[i]){
                max = window[i];
                maxPos = i;
            }
        }
        maxValue = max;
        return maxPos;
    }
public:
    vector<int> maxSlidingWindow(vector<int>& nums, int k) {
        if (nums.empty()){
            return {};
        }
        if (k == 1){
            return nums;
        }
        vector<int> window;
        vector<int> res;
        window.resize(k);
        int max = nums[0];
        int maxPos = 0;
        for (int i = 0; i < k; ++i) {
            window[i] = nums[i];
            if (max <= window[i]){
                max = window[i];
                maxPos = i;
            }
        }
        for (int i = k; i < nums.size(); ++i) {
            res.push_back(max);
            window[i%k] = nums[i];
            if (nums[i] > max){
                maxPos = i%k;
                max = nums[i];
            }else if(i%k == maxPos){
                maxPos = findMax(window,max);
            }
        }
        res.push_back(max);
        return res;
    }
};

int main(){
    Solution solution;
    vector<int> vec{7,2,4};
    solution.maxSlidingWindow(vec,2);
}