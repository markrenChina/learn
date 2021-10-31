//
// Created by mark on 2021/10/20.
//
#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:

    int minMoves(vector<int>& nums) {
        int minNum = *min_element(nums.begin(),nums.end());
        int res = 0;
        for (int num : nums) {
            res += num - minNum;
        }
        return res;
    }
};


int main(){
    Solution solution;
    vector<int> vec = {1,2,3};
    cout << solution.minMoves(vec);
}