//
// Created by Ushop on 2022/8/8.
//
#include "../leetcode-utils.hpp"

class Solution {
public:
    vector<int> spiralOrder(vector<vector<int>>& matrix) {
        if(matrix.empty()){
            return {};
        }
        int rowStart=0;
        int rowEnd = matrix[0].size()-1;
        int colStart=0;
        int colEnd = matrix.size()-1;
        vector<int> res;
        while(rowStart < rowEnd && colStart < colEnd){
            for(int toRight=rowStart; toRight <= rowEnd;toRight++){
                res.push_back(matrix[colStart][toRight]);
            }
            colStart++;
            for(int toBottom=colStart;toBottom <= colEnd;toBottom++){
                res.push_back(matrix[toBottom][rowEnd]);
            }
            rowEnd--;
            for(int toLeft = rowEnd;toLeft >=rowStart;toLeft--){
                res.push_back(matrix[colEnd][toLeft]);
            }
            colEnd--;
            for(int toTop= colEnd;toTop >= colStart; toTop--){
                res.push_back(matrix[toTop][rowStart]);
            }
            rowStart++;
        }
        if(colStart == colEnd){
            for(int toRight=rowStart; toRight <= rowEnd;toRight++){
                res.push_back(matrix[colStart][toRight]);
            }
        }
        if(rowStart == rowEnd && colStart != colEnd){
            for(int toBottom=colStart;toBottom <= colEnd;toBottom++){
                res.push_back(matrix[toBottom][rowEnd]);
            }
        }
        return res;
    }
};

int main(){
    Solution solution;
    vector<vector<int> > vec{{1,2},{3,4}};
    solution.spiralOrder(vec);
}