//
// Created by mark on 2021/10/15.
//
#include <iostream>
#include <vector>

using namespace std;


class Solution {

private:
    string res = "1";
    void _countAndSay() {
        vector<u_char> vec;
        u_char temp = res[0];
        int count = 1;
        for (int i = 1; i < res.size(); ++i) {
            if (res[i] == temp){
                count++;
            } else {
                vec.push_back(u_char (count + '0'));
                vec.push_back(temp);
                temp = res[i];
                count = 1;
            }
        }
        vec.push_back(u_char (count + '0'));
        vec.push_back(temp);
        res.assign(vec.begin(),  vec.end());
    }
public:
    string countAndSay(int n) {
        if (n == 1) {
            return res;
        }
        for (int i = 1; i < n; ++i) {
            _countAndSay();
        }
        return res;
    }
};

int main(){
    Solution solution;
    cout << solution.countAndSay(4) <<endl;
}