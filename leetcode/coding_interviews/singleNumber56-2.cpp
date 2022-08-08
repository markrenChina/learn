//
// Created by Ushop on 2022/8/5.
//
#include "../leetcode-utils.hpp"

//累加二进制位，最后对3取余，拼接整个二进制就是结果
class Solution {
public:
    int singleNumber(vector<int>& nums) {
        unsigned int res = 0;
        int array[32]= {0};
        for(auto n: nums){
            //array 是倒着n的0，1
            for(int i = 0; i < 32 ; i++){
                if(n > 0){
                    if(n & 0x01){
                        array[i]++;
                    }
                }else {
                    break;
                }
                n = (n >> 1);
            }
        }
        for(int i = 0; i < 32; i++){
            res = (res >> 1);
            if(array[i] %3){
                res |= 0x80000000;
            }
            //res = (res >> 1);
        }
        return (int)res;
    }
};

int main(){
    Solution solution;
    vector<int> vec{3,4,3,3};
    cout << solution.singleNumber(vec);
}