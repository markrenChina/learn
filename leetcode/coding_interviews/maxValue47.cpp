//
// Created by Ushop on 2022/7/22.
//
#include "../leetcode-utils.hpp"

class Solution {
public:
//    int maxValue(vector<vector<int>>& grid) {
//        int col = grid.size();
//        int row = grid[0].size();
//        vector<vector<int>> memo;
//        memo.resize(col);
//        memo[0].resize(row);
//        memo[0][0] = grid[0][0];
//        for (int i = 1; i < row; ++i) {
//            memo[0][i] = grid[0][i] + memo[0][i-1];
//        }
//        for (int i = 1; i < col; ++i) {
//            memo[i].resize(row);
//            memo[i][0] = grid[i][0] + memo[i-1][0];
//        }
//        for (int i = 1; i < col; ++i) {
//            for (int j = 1; j < row; ++j) {
//                memo[i][j] = ::max(memo[i-1][j],memo[i][j-1]) + grid[i][j];
//            }
//        }
//        return memo[col-1][row-1];
//    }

    int maxValue(vector<vector<int>>& grid) {
        int col = grid.size();
        int row = grid[0].size();
        vector<int> memo;
        memo.resize(row);
        memo[0] = grid[0][0];
        for (int i = 1; i < row; ++i) {
            memo[i] = grid[0][i] + memo[i-1];
        }
        for (int i = 1; i < col; ++i) {
            memo[0] = memo[0] + grid[i][0];
            for (int j = 1; j < row; ++j) {
                //memo[i][j] = ::max(memo[i-1][j],memo[i][j-1]) + grid[i][j];
                memo[j] = ::max(memo[j],memo[j-1]) + grid[i][j];
            }
        }
        return memo[row-1];
    }
};

int main(){
    Solution solution;
    /*vector<vector<int>> vec = {
            {1,3,1},
            {1,5,1},
            {4,2,1}
    };*/
    vector<vector<int>> vec = {
            {1,2,5},
            {3,2,1}
    };
    cout << solution.maxValue(vec) << endl;
}