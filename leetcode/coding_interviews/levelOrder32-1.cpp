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

//层序遍历
class Solution {
public:
    vector<int> levelOrder(TreeNode *root) {
        queue<TreeNode*> deque;
        vector<int> vec;
        if (!root){
            return vec;
        }
        deque.push(root);
        while (!deque.empty()){
            TreeNode* tmp = deque.front();
            if (tmp->left){
                deque.push(tmp->left);
            }
            if (tmp->right){
                deque.push(tmp->right);
            }
            vec.push_back(tmp->val);
            deque.pop();
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
    //vector<int> vec = solution.levelOrder(root);
    vector<int> vec = solution.levelOrder(nullptr);
    for_each(vec.begin(), vec.end(),[](auto& e){ cout << e <<" "; });
}
