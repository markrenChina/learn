//
// Created by Ushop on 2022/7/19.
//
#include "../leetcode-utils.hpp"
struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;

    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
private:
    void addToStack(stack<TreeNode*>& st,TreeNode* val){
        st.push(val);
    }

public:
    vector<vector<int>> levelOrder(TreeNode* root) {
        vector<vector<int> > vec;
        stack<TreeNode*> st_l;
        stack<TreeNode*> st_r;
        vector<stack<TreeNode*>* > sts{&st_l,&st_r};
        int level = 0;
        int pre_count = 1;
        int next_count = 0;
        if(!root){
            return vec;
        }
        st_l.push(root);
        vec.emplace_back();
        while(!st_l.empty() || !st_r.empty()){
            TreeNode* tmp = sts[level%2]->top();
            --pre_count;
            vec[level].push_back(tmp->val);
            int next_level = level + 1;
            if(next_level%2){
                if(tmp->left){
                    ++next_count;
                    addToStack(st_r,tmp->left);
                }
                if(tmp->right){
                    ++next_count;
                    addToStack(st_r,tmp->right);
                }
            }else {
                if(tmp->right){
                    ++next_count;
                    addToStack(st_l,tmp->right);
                }
                if(tmp->left){
                    ++next_count;
                    addToStack(st_l,tmp->left);
                }
            }
            sts[level%2]->pop();
            if(pre_count == 0){
                ++level;
                if(next_count){
                    vec.emplace_back();
                }
                swap(next_count,pre_count);
            }
        }
        return vec;
    }
};

int main(int argc,char **args){
    Solution solution;
    TreeNode* root = new TreeNode(3);
    TreeNode* root_l = new TreeNode(9);
    TreeNode* root_r = new TreeNode(20);
    TreeNode* root_r_l = new TreeNode(15);
    TreeNode* root_r_r = new TreeNode(7);
    root->left = root_l;
    root->right= root_r;
    root_r->left = root_r_l;
    root_r->right = root_r_r;
    vector<vector<int>> vec = solution.levelOrder(root);
//    vector<vector<int>> vec = solution.levelOrder(nullptr);
    for_each(vec.begin(), vec.end(),[](vector<int>& e){
        for_each(e.begin(), e.end(),[](auto& i ){
            cout << i <<" ";
        });
        cout << endl;
    });
}