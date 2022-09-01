//
// Created by Ushop on 2022/8/17.
//
#include "leetcode-utils.hpp"


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
    vector<pair<int,int>> vals;
    int maxLevel = 0;
    void travers(TreeNode *node, int level) {
        if (!node) {
            return;
        }
        travers(node->left,level+1);
        travers(node->right,level+1);
        if (!node->left && !node->right) {
            vals.emplace_back(level, node->val);
            maxLevel = ::max(maxLevel,level);
        }
    }

public:
    int deepestLeavesSum(TreeNode *root) {
        travers(root, 1);
        int sum = 0;
        for (auto &item: vals){
            if (item.first == maxLevel){
                sum+= item.second;
            }
        }
        return sum;
    }
};

int main(){
    Solution solution;
    vector<int> vec{1,2,3,4,5,NULL,6,7,NULL,NULL,NULL,NULL,NULL,NULL,8};
    TreeNode* root = DeserializeBinaryTree<TreeNode,int>(vec,NULL);
    cout << solution.deepestLeavesSum(root);
}