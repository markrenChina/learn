//
// Created by Ushop on 2022/7/16.
//

#include "../leetcode-utils.hpp"

//string后面是否有足够的空间扩容？
//string是否允许破坏？
//从后向前遍历，避免多次移动
class Solution {
public:
    string replaceSpace(string s) {
        int count = 0;
        for (auto& c: s) {
            if (' ' == c) {
                count++;
            }
        }
        int i = s.size() -1;
        s.resize(s.size()+count*2);
        int j = s.size()-1;
        for (; i >= 0 && j>=0; --i) {
            if (' '== s[i]){
                s[j] = '0';
                s[j-1] = '2';
                s[j-2] = '%';
                j-=3;
            } else {
                s[j] = s[i];
                j--;
            }
        }
        return s;
    }
};

int main(){
//    string s = "We are happy";
    string s = " ";
    Solution solution;
    cout << solution.replaceSpace(s);
}