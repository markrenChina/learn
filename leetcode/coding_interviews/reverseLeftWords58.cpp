//
// Created by Ushop on 2022/7/16.
//
#include "../leetcode-utils.hpp"

//能否破坏原字符串？
//要求空间复制度还是时间复制度？
//这题是另一题目的扩展：
//反转句子，单词不能反转
class Solution {
public:
    string reverseLeftWords(string s, int n) {
//        if (n >= s.size()){
//            //TODO throw
//            return s;
//        }
        /*reverse(s,0,s.size()-1);
        reverse(s,0,s.size()-n-1);
        reverse(s,s.size()-n,s.size()-1);*/

        ::reverse(s.begin(), s.begin() + n);
        ::reverse(s.begin() + n, s.end());
        ::reverse(s.begin(), s.end());
        return s;
    }

private:
    void reverse(string& s,int start, int end){
        char tmp;
        for (int i = 0; i <= (end-start)/2; ++i) {
            tmp = s[i + start];
            s[i + start] = s[end-i];
            s[end-i] = tmp;
        }
    }
};

int main(){
    Solution solution;
    cout << solution.reverseLeftWords("abcdefg",2);
//    cout << solution.reverseLeftWords("",2);
}