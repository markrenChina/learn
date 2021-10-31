//
// Created by mark on 2021/10/18.
//

#include <iostream>

using namespace std;

class Solution {
public:
    int findComplement(int num) {
        int res = !(num & 1);
        long temp = 2;
        while (num >>= 1){
            if((num & 1)!= 1){
                res += temp;
            }
            temp *=2;
        }
        return res;
    }
};

int main() {
    Solution solution;
    cout << solution.findComplement(1);
}