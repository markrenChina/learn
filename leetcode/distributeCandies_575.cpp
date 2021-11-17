//
// Created by mark on 2021/11/1.
//
#include <vector>
#include <iostream>
#include <unordered_set>

using namespace std;

class Solution {
public:
    int distributeCandies(vector<int>& candyType) {
        unordered_set<int> set;
        for (auto & e: candyType) {
            set.insert(e);
        }
        int size = (int )set.size();
        return min(size,(int)candyType.size()/2);
    }
};