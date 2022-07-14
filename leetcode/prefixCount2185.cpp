//
// Created by Ushop on 2022/7/13.
//
#include "leetcode-utils.hpp"

class Solution {
public:
    int prefixCount(vector<string>& words, string pref) {
        int res = 0;
        for(auto& word : words){
            if(word.size() < pref.size()){
                continue;
            }else {
                int i=0;
                for(; i < pref.size(); ++i){
                    if(word[i] != pref[i]){
                        break;
                    }
                }
                if (i == pref.size()){
                    ++res;
                }
            }
        }
        return res;
    }
};

int main(){
    Solution solution;
    vector<string> vec{"leetcode","win","loops","success"};
    cout << solution.prefixCount(vec,"code");
}