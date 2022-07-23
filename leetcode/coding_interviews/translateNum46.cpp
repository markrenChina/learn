//
// Created by Ushop on 2022/7/23.
//
/**
 * 我们有两种不同的选择来翻译第一位数字1，一种是选择数字1翻译成b，后面剩下数字2258（f(i+1)）；第二种是选择1和紧挨着的2一起翻译成m，后面剩下258(f(i+2))。258重复出现。
 * 显然我们可以写一个递归来计算翻译的数目。
 * 我们定义函数f(i)表示从第1位开始的不同的翻译的数目，那么：f(i) = f(i+1)+g(i,i+1)*f(i+2)
 * 当第i位和和第i+1位的值拼接在一起数字在10~25的范围内时，函数g(i,i+1)为1,否则为0。
 * 258重复出现可以避免反复计算，可以从后往前
*/
#include "../leetcode-utils.hpp"

class Solution {
public:
    int translateNum(int num) {
        int tmp = num;
        //反转的列表
        vector<int> list;
        vector<int> res;
        while (tmp){
            list.push_back(tmp%10);
            tmp = tmp/10;
        }
        res.resize(list.size());
        for (int i = 0; i < list.size(); ++i) {
            //f(i) = f(i+1)+g(i,i+1)*f(i+2) 此处是反转的，so
            //res[i] = res[i-1] + ()*res[i-2]
            //去掉边界
            if (i == 0){
                res[0] = 1;
            }else if (i == 1){
                //g函数
                int g = (list[i] *10 + list[i-1]);
                if ( g<=25 && g >= 10){
                    res[1] = 2;
                } else {
                    res[1] = 1;
                }
            }else {
                //g函数
                int g = (list[i] *10 + list[i-1]);
                res[i] = res[i-1] + (g <=25 && g >= 10)*res[i-2];
            }
        }
        return res.empty() ? 1 :res.back();
    }
};

int main() {
    Solution solution;
//    cout << solution.translateNum(12258) << endl;
//    cout << solution.translateNum(25) << endl;
    cout << solution.translateNum(0) << endl;
//    cout << solution.translateNum(506) << endl;
}