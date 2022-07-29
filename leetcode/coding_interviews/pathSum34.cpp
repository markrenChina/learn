//
// Created by Ushop on 2022/7/29.
//

#include "../leetcode-utils.hpp"


struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;

    TreeNode() : val(0), left(nullptr), right(nullptr) {}

    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}

    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

class Solution {
private:
    void next(TreeNode *node, vector<int> &tmp, int sum) {
        sum += node->val;
        if (isLeaf(node)) {
            if (sum == m_target) {
                tmp.push_back(node->val);
                res.push_back(tmp);
                tmp.pop_back();
            }
            return;
        }
        tmp.push_back(node->val);
        if (node->left) {
            next(node->left, tmp, sum);
        }
        if (node->right) {
            next(node->right, tmp, sum);
        }
        tmp.pop_back();
    }

    bool isLeaf(TreeNode *node) {
        return !node->left && !node->right;
    }

public:
    vector<vector<int>> pathSum(TreeNode *root, int target) {
        if (root) {
            m_target = target;
            vector<int> tmp;
            next(root, tmp, 0);
        }
        return res;
    }

private:
    int m_target = 0;
    vector<vector<int>> res{};
};


int main(){
    vector<int> vec = {5,4,8,11, INT32_MAX,13,4,7,2, INT32_MAX, INT32_MAX,INT32_MAX, INT32_MAX,5,1};
    auto* root = DeserializeBinaryTree<TreeNode,int>(vec,INT32_MAX);
    Solution solution;
    auto v = solution.pathSum(root,22);
    cout << endl;
}

