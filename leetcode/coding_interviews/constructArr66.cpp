//
// Created by Ushop on 2022/8/8.
//
#include "../leetcode-utils.hpp"


//f(x) = f(0)..f(x-1) * f(x+1)...f(n-1)
//可以把公式看成以 * 法分割的2部分
//第一个部分是从0开始递增*，第2个部分从n开始递增*
class Solution {
public:
    vector<int> constructArr(vector<int>& a) {
        vector<int> pre,end;
        pre.resize(a.size());
        end.resize(a.size());
        pre[0] = a[0];
        end[a.size()-1] = a[a.size()-1];
        for(int i = 1 ; i < a.size() ; ++i) {
            pre[i] = pre[i-1] * a[i];
            end[a.size()-i-1] = end[a.size()-i] * a[a.size()-1-i];
        }
        vector<int> res;
        res.resize(a.size());
        res[0] = end[1];
        res[a.size()-1] = pre[a.size()-1];
        for(int i = 1 ; i < a.size() -1 ; ++i){
            res[i] = pre[i-1] * end[i+1];
        }
        return res;
    }
};

int main(){
    vector<int> vec {1, 2, 3, 4, 5};
    Solution solution;
    solution.constructArr(vec);
}