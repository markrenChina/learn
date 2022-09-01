//
// Created by Ushop on 2022/8/9.
//
#include "../leetcode-utils.hpp"

class Solution {
private:
    bool g_error= false;

    void trimSpace(string& str,int& scanedPos){
        while(str[scanedPos] == ' '){
            ++scanedPos;
        }
    }

    bool isNegative(string& str,int& scanedPos){
        if(str[scanedPos] == '-'){
            ++scanedPos;
            return true;
        }else if(str[scanedPos] == '+' ) {
            ++scanedPos;
            return false;
        }else if (str[scanedPos] < '0' || str[scanedPos] > '9'){
            g_error = true;
        }
        return false;
    }
    long strToLong(string& str,int& scanedPos){
        long res = 0;
        int index = 0;
        while(str[scanedPos]>='0'&&str[scanedPos] <= '9' && index <= 11){
            res = res*10 + (str[scanedPos] - '0');
            ++scanedPos;
            if(res > 0){
                ++index;
            }
        }
        if(index == 0){
            g_error = true;
        }
        return res;
    }
public:
    int strToInt(string str) {
        if(str.empty()){
            return 0;
        }
        int scanedPos = 0;
        trimSpace(str,scanedPos);
        bool negative = isNegative(str,scanedPos);
        if(g_error){
            return 0;
        }
        long res = strToLong(str,scanedPos);
        if(g_error){
            return 0;
        }
        if(negative){
            res = -res;
            if(res < INT32_MIN){
                return INT32_MIN;
            }else {
                return (int)res;
            }
        }else {
            if(res > INT32_MAX){
                return INT32_MAX;
            }else {
                return (int)res;
            }
        }
    }
};

int main(){
    Solution solution;
    cout << solution.strToInt("20000000000000000000") << endl;
}