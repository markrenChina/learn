//
// Created by mark on 2021/10/22.
//
#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

class Solution {
private:

public:
    vector<int> majorityElement(vector<int>& nums) {
        if (nums.size() == 2){
            if (nums[0] == nums[1]){
                return vector<int>() = {nums[0]};
            }
            return vector<int>() = {nums[0],nums[1]};
        } else if (nums.size() == 1){
            return vector<int>() = {nums[0]};
        }
        vector<int> vec;
        sort(nums.begin(),nums.end());
        int step = ((int)nums.size()/3) + 1;
        int count = 1;
        int temp = nums[0];
        for (int i = 1; i < nums.size(); ++i) {
            if (temp != nums[i]){
                if (count >= step){
                    vec.push_back(temp);
                }
                count = 1;
                temp = nums[i];
            } else {
                count++;
            }
        }
        if (count >= step){
            vec.push_back(temp);
        }
        return vec;
    }
};

int main(){
    Solution solution;
    vector<int> vec =  solution.majorityElement(vector<int>() = {1,2});
    for (auto e:vec) {
        cout << e << " ";
    }
}