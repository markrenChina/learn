//
// Created by Ushop on 2022/7/1.
//
#include <utility>
#include <vector>
#include <string>
#include <iostream>
#include <stack>
#include <unordered_set>

using namespace std;

class Solution {


public:
    vector<int> diffWaysToCompute(string expression) {
        return _diffWaysToCompute(expression);
    }

private:
    vector<int> _diffWaysToCompute(string expression) {
        if (expression.empty()){
            return {};
        }

        vector<int> res;
        vector<int> oper;
        vector<int> values;
        pareExpression(values,oper,expression);
        if (oper.empty()){
            return values;
        }
        for (int i = 0; i < oper.size(); ++i) {
            int preSize = i;
            for (int j = 0; j <= i ; ++j) {
                if (values[j] >= 10){
                    preSize +=2;
                } else {
                    preSize +=1;
                }
            }
            vector<int> left = _diffWaysToCompute(expression.substr(0,preSize));
            vector<int> right = _diffWaysToCompute(expression.substr(preSize+1));
            for (int j : left) {
                for (int k : right) {
                    res.push_back(calculate(oper[i],j,k));
                }
            }
        }
        return res;
    }

    int calculate(int oper,int left,int right){
        switch (oper) {
            default:
            case (int)'+': return left+right;
            case (int)'-': return left-right;
            case (int)'*': return left*right;
        }
    }

    void pareExpression(vector<int>& values,vector<int>& oper,string& expression){
        int temp  = 0;
        for (int i = 0; i < expression.size(); ++i) {
            char c =  expression[i];
            if ((int)c >= (int)'0'&& (int)c <= (int)'9'){
                temp = temp *10 + (int)c - (int )'0';
            } else{
                values.push_back(temp);
                temp = 0;
                oper.push_back((int)c);
            }
        }
        values.push_back(temp);
    }
};


void printfVector(vector<int> vec){
    cout << "[";
    for (auto& e:vec) {
        cout << e <<",";
    }
    cout<< "]" << endl;
}

int main(){
//    Solution solution1;
//    vector<int> bak1 = solution1.diffWaysToCompute("2-1-1");
//    printfVector(bak1);
    Solution solution2;
    vector<int> bak2 = solution2.diffWaysToCompute("10+5");
    printfVector(bak2);
}