//
// Created by Ushop on 2022/7/29.
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
    void _kthLargest(TreeNode *root) {
        if (!root || m_k <= 0) {
            return;
        }
        if (root->right) {
            _kthLargest(root->right);
        }
        m_k--;
        if (m_k == 0) {
            res = root->val;
            return;
        }
        if (root->left) {
            _kthLargest(root->left);
        }
    }

    int m_k = 0;
    int res = 0;
public:
    int kthLargest(TreeNode *root, int k) {
        m_k = k;
        _kthLargest(root);
        return res;
    }
};


int main(){
//    TreeNode* root = DeserializeBinaryTree<TreeNode,int>({3,1,4,NULL,2},NULL);
    TreeNode* root = DeserializeBinaryTree<TreeNode,int>({3,1,4,NULL,2},NULL);
    Solution solution;
    cout << solution.kthLargest(root,1);
}