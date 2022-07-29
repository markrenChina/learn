//
// Created by Ushop on 2022/7/26.
//
#include "../leetcode-utils.hpp"


class Solution {
public:
    vector<int> exchange(vector<int>& nums) {
        if(nums.empty()){
            return nums;
        }
        int jishuPos = 0;
        int oushuPos = nums.size()-1;
        while(jishuPos != oushuPos){
            if(nums[jishuPos] % 2 == 1){
                jishuPos++;
            }else {
                //jishuPos 因为偶数进入，循环后可能oushuPos = jishuPos-1
                while(nums[oushuPos] %2 == 0 && oushuPos > jishuPos){
                    oushuPos--;
                }
                if(jishuPos == oushuPos){
                    break;
                }
                ::swap(nums[jishuPos],nums[oushuPos]);
                oushuPos--;
            }
        }
        return nums;
    }
};

int main(){
    Solution solution;
    vector<int> vec {8,10,3,20,12,4,10,8,4,0,5,17,7,20,3};
    solution.exchange(vec);
}