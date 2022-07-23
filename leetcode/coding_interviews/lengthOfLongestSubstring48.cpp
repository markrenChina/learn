//
// Created by Ushop on 2022/7/23.
//
#include "../leetcode-utils.hpp"

class Solution {
public:
    int lengthOfLongestSubstring(string s) {
        if (s.size() == 0){
            return 0;
        }
        vector<int> res;
        vector<int> bak;
        bak.resize(256);
        res.resize(s.size());
        res[0] = 1;
        int max = 1;
        //0索引与初始化0冲突，所以标记从1开始
        bak[s[0]] = 1;
        for(int i = 1; i < s.size(); ++i){
            int tmpIndex = s[i];
            if(bak[tmpIndex] == 0){
                res[i] = res[i-1] + 1;
            }else {
                //上次出现的距离
                int dis = i - bak[tmpIndex] + 1 ;
                if(dis <= res[i-1]){
                    res[i] = dis;
                }else{
                    res[i] = res[i-1] + 1;
                }
            }
            bak[tmpIndex] = i + 1;
            if(max < res[i]){
                max = res[i];
            }
        }
        return max;
    }
};

int main() {
    Solution solution;
//    cout << solution.lengthOfLongestSubstring("nnnnn")  << " == 1"<< endl;
//    cout << solution.lengthOfLongestSubstring("abcabcbb")<< " == 3" << endl;
//    cout << solution.lengthOfLongestSubstring("pwwkew")<< " == 3" << endl;
//    cout << solution.lengthOfLongestSubstring("")<< " == 0" << endl;
    cout << solution.lengthOfLongestSubstring(" ")<< " == 1" << endl;
}
