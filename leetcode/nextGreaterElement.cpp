//
// Created by mark on 2021/10/26.
//

#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    vector<int> nextGreaterElement(vector<int>& nums1, vector<int>& nums2) {
        vector<int> res;
        for (int i : nums1) {
            int temp = -1;
            for (int j : nums2){
                if (j == i){
                    temp = j;
                }
                if (temp != -1 && i < j){
                    res.push_back(j);
                    goto outside;
                }
            }
            res.push_back(-1);
            outside:;
        }
        return res;
    }

    vector<int> nextGreaterElement(vector<int>& nums1, vector<int>& nums2) {
        
        return res;
    }
};