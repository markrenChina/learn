//
// Created by mark on 2021/10/25.
//

#include <iostream>
#include <vector>

using namespace std;

class Solution {
private:
    bool res = false;

    void bSearchMatrix(vector<vector<int>> &matrix, int target,int startY, int endY) {
        if (startY == endY || startY +1 == endY){
            for (int i = endY; i >= 0; --i) {
                res = binary_search(matrix[i].begin(),matrix[i].end(),target);
                if (res){
                    return;
                }
            }
            return;
        }
        int mindy = (startY + endY) >> 1;
        if (matrix[mindy][0] > target){
            bSearchMatrix(matrix,target,startY,mindy-1);
        } else if (matrix[mindy][0] < target){
            bSearchMatrix(matrix,target,mindy ,endY);
        } else {
            res = true;
        }
    }

public:
    bool searchMatrix(vector<vector<int>> &matrix, int target) {
        bSearchMatrix(matrix, target,  0, matrix.size()-1);
        return res;
    }
};

int main() {
    vector<vector<int>> vec = {
            vector<int>() = {1, 4, 7, 11, 15},
            vector<int>() = {2, 5, 8, 12, 19},
            vector<int>() = {3, 6, 9, 16, 22},
            vector<int>() = {10, 13, 14, 17, 24},
            vector<int>() = {18, 21, 23, 26, 30}
    };
    Solution solution;
    cout<< solution.searchMatrix(vec, 18);
}
