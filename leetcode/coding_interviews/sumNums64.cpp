#include "../leetcode-utils.hpp"

vector<function<int(int)>> funs;

int _sumNums(int n) {
    return n+funs[!!n](n-1);
}
int _sumEnd(int n){
    return 0;
}
class Solution {
public:
    Solution() {
        funs = {_sumEnd, _sumNums};
    }

    int sumNums(int n) {
        return n+funs[!!n](n-1);
    }
};

int main(){
    Solution solution;
    cout << solution.sumNums(3);
}