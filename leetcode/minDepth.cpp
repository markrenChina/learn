//
// Created by mark on 2021/10/4.
//
#include <iostream>
#include <queue>

using namespace std;

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;

    TreeNode() : val(0), left(nullptr), right(nullptr) {}

    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}

    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

int _minDepth(TreeNode* root){
    if(root->left != nullptr && root->right != nullptr){
        return min(_minDepth(root->left),_minDepth(root ->right))+1;
    }
    if(root->left != nullptr){
        return _minDepth(root->left) + 1;
    }
    if(root->right != nullptr){
        return _minDepth(root ->right) + 1;
    }
    return 1;
}

queue<TreeNode*> nodes;
int level = 0;
TreeNode* node;

void _minDepthBylevelTraverse(){
    level++;
    bool finded = false;
    int size = nodes.size();
    for(int i = 0;i < size; i++ ){
        node = nodes.front();
        nodes.pop();
        if(node ->left){
            nodes.push(node->left);
        }else if (node ->right == nullptr){
            finded = true;
            break;
        }
        if(node ->right){
            nodes.push(node->right);
        }
    }
    if(!finded){
        _minDepthBylevelTraverse();
    }
}

int minDepth(TreeNode* root) {
    if(root == nullptr){
        return 0;
    }
    //普通递归解法
    //return _minDepth(root);
    //层序遍历解法
    nodes.push(root);
    _minDepthBylevelTraverse();
    return level;
}