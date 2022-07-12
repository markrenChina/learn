//
// Created by mark on 2022/7/4.
//
#include <vector>
#include <iostream>

using namespace std;


class Solution {
public:
    vector<vector<int>> minimumAbsDifference(vector<int>& arr) {
        std::ios::sync_with_stdio(false);//消除输入输出缓存
        std::cin.tie(0);//解除cin与cout 的绑定
        sort(arr.begin(),arr.end());
        int diff = INT32_MAX;
        for (int i = 1; i < arr.size(); ++i) {
            diff = min(diff,arr[i]-arr[i-1]);
        }
        for (int i = 1; i < arr.size(); ++i) {
            if (arr[i] - arr[i-1] == diff){
                res.push_back({arr[i-1],arr[i]});
            }
        }
        return res;
    }
private:

    vector<vector<int>> res;
};



int main(){
    vector<int> arr = {1,3,6,10,15};
    Solution solution;
    vector<vector<int>> res = solution.minimumAbsDifference(arr);
    for (auto& vec:res) {
        cout << "[" << vec[0] << "," << vec[1]<< "],";cout ;
    }
    cout << endl;
}