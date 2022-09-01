//
// Created by Ushop on 2022/8/31.
//
#include "leetcode-utils.hpp"

class Solution {
public:
    bool validateStackSequences(vector<int>& pushed, vector<int>& popped) {
        stack<int> st;
        int poppedPos = 0;
        for(int i = 0; i < pushed.size(); i++){
            st.push(pushed[i]);
            while(!st.empty() && st.top() == popped[poppedPos]){
                poppedPos++;
                st.pop();
            }
        }
        return st.empty();
    }
};

int main(){
    Solution solution;
//    vector<int> pushed{1,2,3,4,5};
    vector<int> pushed{1,0,2};
//    vector<int> popped{4,5,3,2,1};
    vector<int> popped{2,1,0};
    cout << solution.validateStackSequences(pushed,popped) << endl;
}