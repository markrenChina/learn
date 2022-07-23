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
//2. 优化->和比对一起，一起比对是否等于根值和根的子值，减少遍历
    bool contains(TreeNode* parent,TreeNode* child){
        if(!parent){
            return false;
        }
        if(parent->val == child->val){
            TreeNode* tmp1 = parent;
            TreeNode* tmp2 = child;
            if(isSame(tmp1,tmp2)){
                return true;
            }
        }
        return contains(parent->left,child) ||
               contains(parent->right,child);
    }

//1. 优化 ->递归改循环，失败直接返回，不比对。
    bool isSame(TreeNode* one, TreeNode* two) {
        if(one == nullptr && two == nullptr){
            return true;
        }else if(one != nullptr && two != nullptr){
            return (one->val == two->val) &&
                   isSame(one->left,two->left) &&
                   isSame(one->right,two->right);
        }else if (two == nullptr){
            return true;
        } else {
            return false;
        }
    }

public:
    bool isSubStructure(TreeNode* A, TreeNode* B) {
        if(!B){
            return false;
        }
        this->parent = A;
        this->child = B;
        return contains(parent,child);
    }
private:
    TreeNode* parent;
    TreeNode* child;
};

int main(){
    Solution solution;
    TreeNode* A = new TreeNode(10);
    TreeNode* A_l = new TreeNode(12);
    TreeNode* A_r = new TreeNode(6);
    TreeNode* A_l_l = new TreeNode(8);
    TreeNode* B = new TreeNode(10);

}