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
public:
    vector<vector<int>> levelOrder(TreeNode* root) {
        vector<vector<int> > vec;
        queue<TreeNode*> queue;
        int pre_count = 1;
        int next_count = 0;
        int level = 0;
        if(!root){
            return vec;
        }
        queue.push(root);
        vec.emplace_back();
        while(!queue.empty()){
            TreeNode* tmp = queue.front();
            --pre_count;
            vec[level].push_back(tmp->val);
            if(tmp->left){
                ++next_count;
                queue.push(tmp->left);
            }
            if(tmp->right){
                ++next_count;
                queue.push(tmp->right);
            }
            if(pre_count == 0){
                ++level;
                if(next_count){
                    vec.emplace_back();
                }
                swap(pre_count,next_count);
            }
            queue.pop();
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