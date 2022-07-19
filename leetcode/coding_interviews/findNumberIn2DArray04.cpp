//
// Created by Ushop on 2022/7/18.
//
/*
 * 输入 **matrix 是长度为 matrixSize 的数组指针的数组，其中每个元素（也是一个数组）
 * 的长度组成 *matrixColSize 数组作为另一输入，*matrixColSize 数组的长度也为 matrixSize
 */
//#include <stdio.h>
#include "../leetcode-utils.hpp"


class Solution {
public:
    bool findNumberIn2DArray(vector<vector<int>> &matrix, int target) {
        if (matrix.empty()){
            return false;
        }
        int maxRow = (int)matrix[0].size() - 1;
        size_t maxCol = 0;
        for (; maxRow >= 0; --maxRow) {
            if (matrix[0][maxRow] == target){
                return true;
            }else if (matrix[0][maxRow] < target) {
                break;
            }
        }
        if (maxRow == -1) {
            return false;
        }
        for (; maxCol < matrix.size() && maxRow >= 0; ++maxCol) {
            if (matrix[maxCol][maxRow] == target) {
                return true;
            } else if (matrix[maxCol][maxRow] > target) {
                for (; maxRow >= 0; --maxRow) {
                    if (matrix[maxCol][maxRow] == target) {
                        return true;
                    } else if (matrix[maxCol][maxRow] < target){
                        break;
                    }
                }
            }
        }
        return false;
    }

};

//从右上角开始往左剔除列
//从剩余的每一行开始剔除行
//bool findNumberIn2DArray(int** matrix, int matrixSize, int* matrixColSize, int target){
//    if(matrix == NULL || matrixSize < 0 || *matrixColSize < 0){
//        return false;
//    }
//    int maxRow = matrixSize;
//    int maxCol = 0;
//    for(; maxRow >= 0 ;-- maxRow ){
//        if(matrix[0][maxRow] > target) {
//            break;
//        }
//    }
//    if(maxRow == -1){
//        return false;
//    }
//    for(; maxCol < *matrixColSize -1; ++maxCol){
//        if(matrix[maxCol][maxRow] == target){
//            return true;
//        }else if(matrix[maxCol][maxRow]> target){
//            for(;maxRow >=0;--maxRow){
//                if(matrix[maxCol][maxRow] == target){
//                    return true;
//                }
//            }
//            break;
//        }
//    }
//    return false;
//}

int main() {
    Solution solution;
    /*vector<vector<int>> vec = {
            {1, 4, 7, 11, 15},
            {2, 5, 8, 12, 19},
            {3, 6, 9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30}
    };*/
    /*vector<vector<int>> vec = {
            {-5},
    };*/
    vector<vector<int>> vec = {
            {1},
            {3},
            {5}
    };
    cout << solution.findNumberIn2DArray(vec,2);
}