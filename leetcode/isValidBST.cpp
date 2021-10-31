//
// Created by mark on 2021/10/12.
//
#include <iostream>
#include <vector>

using namespace std;

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
    vector<int> vector;
    bool isBST = true;
public:
    //中序遍历把值插入数组
    void push_val(TreeNode* root){
        if (root != nullptr){
            push_val(root->left);
            vector.push_back(root->val);
            push_val(root->right);
        }
    }

    //判断vector是否有序
    void _isValidBST(){
        for (int i = 0; i < vector.size()-1; ++i) {
            if (vector[i] >= vector[i +1]){
                isBST = false;
                return;
            }
        }
    }

    bool isValidBST(TreeNode* root) {
        push_val(root);
        _isValidBST();
        return isBST;
    }
};