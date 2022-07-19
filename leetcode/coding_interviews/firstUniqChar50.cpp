//
// Created by Ushop on 2022/7/18.
//
#include "../leetcode-utils.hpp"

class Solution {
public:
    char firstUniqChar(string s) {
        if(s.size() == 0){
            return ' ';
        }
        int array[256];
        for (int & i : array) {
            i = 0;
        }
        for(auto& e: s){
            array[e]++;
        }
        for(auto& e: s){
            if(array[e]== 1) return e;
        }
        return ' ';
    }
};

int main(){
    Solution solution;
    cout << solution.firstUniqChar("abaccdeff") << endl;
    cout << solution.firstUniqChar("") << endl;
}