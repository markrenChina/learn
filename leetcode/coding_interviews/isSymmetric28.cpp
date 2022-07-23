//
// Created by Ushop on 2022/7/20.
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
    bool _isSymmetric(TreeNode *left, TreeNode *right) {
        if (!left && !right){
            return true;
        }
        if (!left || !right){
            return false;
        }
        if (left->val != right->val){
            return false;
        }
        return _isSymmetric(left->left,right->right) &&
                _isSymmetric(left->right,right->left);
    }

public:
    bool isSymmetric(TreeNode *root) {
        if (!root) {
            return false;
        }
        return _isSymmetric(root->left, root->right);
    }
};

void test1(){
    TreeNode* root = new TreeNode(1);
    TreeNode* root_l = new TreeNode(2);
    TreeNode* root_l_l = new TreeNode(3);
    TreeNode* root_l_r = new TreeNode(4);
    TreeNode* root_r = new TreeNode(2);
    TreeNode* root_r_l = new TreeNode(4);
    TreeNode* root_r_r = new TreeNode(3);
    root->left=root_l;
    root->right=root_r;
    root_l->left=root_l_l;
    root_l->right=root_l_r;
    root_r->left=root_r_l;
    root_r->right=root_r_r;
    Solution solution;
    cout << solution.isSymmetric(root) << "== 1" << endl;
}
void test2(){
    TreeNode* root = new TreeNode(1);
    TreeNode* root_l = new TreeNode(2);
    TreeNode* root_r = new TreeNode(3);
    root->left=root_l;
    root->right=root_r;
    Solution solution;
    cout << solution.isSymmetric(root) <<"== 0" << endl;
}

int main(){
    test1();
    test2() ;
}