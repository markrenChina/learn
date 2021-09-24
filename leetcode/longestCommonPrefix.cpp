//
// Created by mark on 2021/9/24.
//

#include <iostream>
#include <vector>

using namespace std;

string longestCommonPrefix(vector<string>& strs) {
    string res = strs[0];
    int resIndex = (int)strs[0].length();
    for (int i = 1; i < strs.size(); ++i){
        for (int j = 0; j < resIndex && j < strs[i].length()+1; ++j){
            if (res[j] != strs[i][j]){
                resIndex = j;
                break;
            }
        }
    }
    res = res.substr(0,resIndex);
    return res;
}

int main() {
    vector<string> vector1{"flower","flow","flight"};
    vector<string> vector2{"dog","racecar","car"};
    vector<string> vector3{"car"};
    vector<string> vector4{"c","acc","ccc"};
    vector<string> vector5{"ab","a"};

    cout << longestCommonPrefix(vector1) <<endl;
    cout << longestCommonPrefix(vector2) <<endl;
    cout << longestCommonPrefix(vector3) <<endl;
    cout << longestCommonPrefix(vector5) <<endl;
}