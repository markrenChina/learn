//
// Created by Ushop on 2022/8/15.
//
#include "../leetcode-utils.hpp"

class Solution {
public:
    int cuttingRope(int n) {
        if(n==2){
            return 1;
        }
        if(n==3){
            return 2;
        }
        int count = 0;
        long long res = 1;
        long long bRes = 1;
        while(n > 1){
            count++;
            n = n-2;
        }
        // 3*3 > 2*2*2
        while(count >= 3){
            bRes = res;
            res = (res * 9) % 1000000007;
            count = count -3;
        }
        if(count == 1){
            if(n==0){
                //2 => 1*1
                //2*2*2*2 = 16 < 3*3*2=18
                return (2 * res) % 1000000007;
            }else {
                //3 => 1*1*2
                //9 => 2*2*2*3 = 24 < 3*3*3 = 27
                return (3 * res) % 1000000007;
            }
        }else if(count == 2){
            if(n==0){
                // 4 => 2*2 > 3*1
                //2*2*2*2*2=32 <  3*3*4=36
                return (2*2*res) % 1000000007;
            }else {
                // 5 => 2*3
                //2*2*2*2*3 = 48 < 2*2*2*2*3=
                return (2*3*res) % 1000000007;
            }
        }else {
            //count = 0
            if(n != 0){
                // 7 => 3*2*2
                // 2*2*2*1 =9
//                res = res/9;
                res = bRes*12 % 1000000007;
            }
        }
        return res;
    }
};

int main(){
    Solution solution;
    cout << solution.cuttingRope(127);
}