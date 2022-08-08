//
// Created by Ushop on 2022/8/5.
//
#include "../leetcode-utils.hpp"

//如果只要一个数字不重复出现，则全数组异或之后的值就是这个数字
//数字中有2各数字不重复，则全数组异或后肯定不等于0，找到其中一个1作为标记
//利用标记位分割数组，重复第一步找出2个数字。
class Solution {
public:
    vector<int> singleNumbers(vector<int>& nums) {
        int flag = 0;
        for(auto& n : nums){
            flag = flag ^ n;
        }
        int num1 = 0,num2 = 0, flag2 = 0x01;
        while((flag & 0x01) != 1){
            flag = (flag >> 1);
            flag2 = flag2 << 1;
        }
        for(auto& n: nums){
            if(n & flag2){
                num1 = num1 ^ n;
            }else {
                num2 = num2 ^ n;
            }
        }
        return {num1,num2};

    }
};