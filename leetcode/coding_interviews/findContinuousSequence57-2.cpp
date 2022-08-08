//
// Created by Ushop on 2022/8/8.
//
#include "../leetcode-utils.hpp"


//在57题1的基础上，定义最小值和最大值
//最小值一直加到最大值 小于 目标 则增加最大值
//等于输出
//最小值一直加到最大值 大于 目标 则增加最小值
//最小值边界为目标的一半
class Solution {
public:
    vector<vector<int>> findContinuousSequence(int target) {
        if(target == 1){
            return {{1}};
        }
        if(target == 2){
            return {{2}};
        }
        int small = 1;
        int big = 2;
        int sum = small+big;
        vector<vector<int>> res;
        while(small <= target/2){
            if(sum < target){
                big++;
                sum +=big;
            }else if(sum > target){
                sum-=small;
                small++;
            }else {
                if(small == big){
                    break;
                }
                vector<int> tmp;
                int n = big-small+1;
                tmp.resize(n);
                for(int i=0;i <n;++i){
                    tmp[i] = small+i;
                }
                res.push_back(tmp);
            }
        }
        return res;
    }
};

int main(){
    Solution solution;
    solution.findContinuousSequence(9);
}