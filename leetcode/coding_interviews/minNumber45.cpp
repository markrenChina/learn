//
// Created by Ushop on 2022/7/29.
//
#include "../leetcode-utils.hpp"

class Solution {
public:
    string minNumber(vector<int>& nums) {
        vector<int> cs;
        cs.resize(10);
        for(auto& n : nums){
            while(n){
                cs[n%10]++;
                n=n/10;
            }
        }
        int len = cs[0];
        char preChar = '0';
        for(int i=1;i<10;i++){
            len += cs[i];
            if(cs[i] != 0 && preChar =='0'){
                preChar = '0' + i;
                --cs[i];
            }
        }
        string res;
        res.resize(len);
        res[0] = preChar;
        int i = 1;
        for(int j = 0;j<10;j++){
            for(;cs[j]>0;--cs[j],++i){
                res[i] = j + '0';
            }
        }
        return res;
    }
};

int main(){
    Solution solution;
    vector<int> nums{2048};
    cout << solution.minNumber(nums);
}