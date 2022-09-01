//
// Created by Ushop on 2022/8/11.
//
#include "../leetcode-utils.hpp"

//.跳过一个字符，继续匹配
//*非确定有限状态机，可以移动p2位，或者不移动，或者移动s2位
class Solution {
private:
    bool _isMatch(string& s,string& p,size_t matchPos,size_t patternPos){
        if (patternPos == p.size()){
            return (patternPos == p.size()) && (matchPos == s.size()) ;
        }
        if (patternPos < p.size()-1 && p[patternPos + 1] == '*'){
            if ((p[patternPos] == '.' && matchPos < s.size()) || s[matchPos] == p[patternPos]) {
                return _isMatch(s,p,matchPos+1,patternPos+2)  //跳入下一个状态
                || _isMatch(s,p,matchPos+1,patternPos)     //保留状态
                || _isMatch(s,p,matchPos,patternPos +2);    //忽略
            } else {
                return _isMatch(s,p,matchPos,patternPos+2);
            }
        }
        if ((p[patternPos] == '.' && matchPos < s.size()) || p[patternPos] == s[matchPos]) {
            return _isMatch(s,p,matchPos+1,patternPos+1);
        } else {
            return false;
        }
    }
public:
    bool isMatch(string s, string p) {
        return _isMatch(s,p,0,0);
    }
};

int main(){
    Solution solution;
    cout << solution.isMatch("bccbbabcaccacbcacaa",
                             ".*b.*c*.*.*.c*a*.c");
}