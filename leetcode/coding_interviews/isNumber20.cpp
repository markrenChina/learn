//
// Created by Ushop on 2022/8/9.
//
#include "../leetcode-utils.hpp"

//数字的格式可以用A[.[B]][e|EC]或者.B[e|EC]表示
//其中A和C都是整数（可以正负），而B是一个无符号整数。
class Solution {
private:
    //扫描符号
    bool scanInteget(string& s,int& scanPos){
        if (s[scanPos] == '+'||s[scanPos] == '-'){
            ++scanPos;
        }
        return scanUnsingedInteget(s,scanPos);
    }

    //扫描无符号整数
    bool scanUnsingedInteget(string& s,int& scanPos){
        int beginPos = scanPos;
        while(scanPos < s.size() && s[scanPos] >= '0' && s[scanPos] <= '9'){
            ++scanPos;
        }
        return scanPos > beginPos;
    }

    void trim(string& s,int& scanPos){
        while(s[scanPos] == ' '){
            ++scanPos;
        }
    }

public:
    bool isNumber(string s) {
        if(s.empty()){
            return false;
        }
        int scanPos = 0;
        trim(s,scanPos);
        bool numeric = scanInteget(s,scanPos);
        if (s[scanPos] == '.'){
            ++scanPos;
            //下面一行代码用||的原因
            //1.小数可以没用整数部分，如.123等于0.123
            //2.小数点后面可以没用数字，如233.等于233.0
            //3.当然，小数点前面和后面可以都有数字，如233.666
            //numeric 必须放右边不然numeric为true时，不执行scanUnsigedInteger
            numeric = scanUnsingedInteget(s,scanPos) || numeric;
        }
        //如果出现‘e'或者’E‘,则接下来是数字的指数部分
        if (s[scanPos] == 'e' || s[scanPos] == 'E'){
            ++scanPos;
            //下面一行代码用&&的原因
            //1.当e或E前面没有数字时，整个字符串不能表示数字，如.e1、E1;
            //2.当e或E后面没有整数时，整数字符串不能表示数字，如12e、12e+5.4；
            numeric = numeric && scanInteget(s,scanPos);
        }
        trim(s,scanPos);
        return numeric && scanPos == s.size();
    }
};

int main(){
    Solution solution;
    solution.isNumber("0.8");
}