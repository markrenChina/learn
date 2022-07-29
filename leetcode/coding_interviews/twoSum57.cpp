//
// Created by Ushop on 2022/7/26.
//
#include "../leetcode-utils.hpp"

class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        unordered_set<int> bak;
        vector<int> res;
        /*for(auto& n : nums){
            auto it = bak.find(n);
            if(it != bak.end()){
                res.resize(2);
                res[0] = n;
                res[1] = target - n;
                break;
            }
            bak.insert(target - n);
        }*/
        int j = 0;
        for(int i = nums.size()-1 ; i > j ; --i ){
            if(nums[i] > target) {
                continue;
            }else {
                while((nums[j] + nums[i]) < target){  // 31  + 10   30 + 10
                    ++j;
                }
                if((nums[j] + nums[i]) == target){
                    res.resize(2);
                    res[0] = nums[i];
                    res[1] = nums[j];
                    break;
                }
            }
        }
        return res;
    }
};

int main(){
    Solution solution;
    vector<int> vec {10,26,30,31,47,60};
    solution.twoSum(vec,40);
}