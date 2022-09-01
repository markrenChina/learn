//
// Created by Ushop on 2022/8/15.
//
#include "../leetcode-utils.hpp"

// n 按照10进制拆成0~9 最后一位1 + 当前位数
class Solution {
public:
    int countDigitOne(int n) {
        //倒着的
        int sum;
        vector<int> vec;
        while(n){
            vec.push_back(n%10);
            n= n/10;
        }
        int times = 1;
        for (int i = 0; i < vec.size(); ++i) {
            if (vec[i] == 0){

            }else if (vec[i] == 1){

            }else {

            }
            times *= 10;
        }
    }
};