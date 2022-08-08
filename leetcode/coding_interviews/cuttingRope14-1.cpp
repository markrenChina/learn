//
// Created by Ushop on 2022/8/8.
//
#include "../leetcode-utils.hpp"

//2*2*2不如 3*3
//2*2 = 4 > 3*1
//2*2*2*2*2 = 32 / 5*5 = 25
//n小于等于2 才切1
//2*能合并为3*3的合并，不能则4
class Solution {
public:
    int cuttingRope(int n) {
        if(n==2){
            return 1;
        }
        int count = 0;
        long res = 1;
        while(n > 1){
            count++;
            n = n-2;
        }
        // 3*3 > 2*2*2
        while(count >= 3){
            res = res * 9;
            count = count -3;
        }
        if(count == 1){
            if(n==0){
                //2 => 1*1
                //2*2*2*2 = 16 < 3*3*2=18
                return 2 * res;
            }else {
                //3 => 1*1*2
                //9 => 2*2*2*3 = 24 < 3*3*3 = 27
                return 2 * res;
            }
        }else if(count == 2){
            if(n==0){
                // 4 => 2*2 > 3*1
                //2*2*2*2*2=32 <  3*3*4=36
                return 2*2*res;
            }else {
                // 5 => 2*3
                //2*2*2*2*3 = 48 < 2*2*2*2*3=
                return 2*3*res;
            }
        }else {
            //count = 0
            if(n != 0){
                // 7 => 3*2*2
                // 2*2*2*1 =9
                res = res/9;
                res = res*12;
            }
        }
        return res;
    }
};

int main(){
    Solution solution;
    solution.cuttingRope(9);
}