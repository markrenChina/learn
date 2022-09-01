//
// Created by Ushop on 2022/8/17.
//
#include "../leetcode-utils.hpp"
#include "../binaryTree.h"

class Solution {
    //在父节点右边，自身左边 s 9 p 3 t 20
    bool isLeft(int selfVal, TreeNode* parentNode ,int targetVal,vector<int>& inorder){
        bool isBegin = (parentNode == nullptr);
        for (auto &item: inorder){
            if (isBegin){
                if (item == selfVal){
                    return true;
                }
                if (parentNode && item == parentNode->val){
                    return false;
                }
            }else if (item == targetVal){
                isBegin = true;
            }
        }
        return false;
    }

    //在父节点的左边，自身的右边 s 2 p 1 t 4
    bool isRight(int selfVal , int parentVal, int targetVal, vector<int>& inorder){
        bool isBegin = false;
        for (auto &item: inorder){
            if (item == selfVal){
                isBegin = true;
            }
            //TODO 有问题
            if (item == parentVal){
                return false;
            }
            if (isBegin){
                if (item == targetVal){
                    return true;
                }
                if (item == parentVal){
                    return false;
                }
            }

        }
        return false;
    }

    TreeNode* deserialzation(vector<int>& preorder,int& prePos, vector<int>& inorder,TreeNode* parentNode){
        if (prePos >= preorder.size()){
            return nullptr;
        }
        TreeNode* treeNode = new TreeNode(preorder[prePos]);
        ++prePos;
        if (prePos < preorder.size() &&
        isLeft(treeNode->val,parentNode,preorder[prePos],inorder)){
            treeNode->left = deserialzation(preorder,prePos,inorder,treeNode);
        }
        if (prePos < preorder.size() &&
        (parentNode == nullptr || isRight(treeNode->val,parentNode->val,preorder[prePos],inorder))) {
            treeNode->right = deserialzation(preorder,prePos,inorder,treeNode);
        }

        return treeNode;
    }
public:
    TreeNode* buildTree(vector<int>& preorder, vector<int>& inorder) {
        int prePos = 0;
        return deserialzation(preorder,prePos,inorder, nullptr);
    }
};

int main(){
    Solution solution;
//    vector<int> pre{3,1,2,4};
    vector<int> pre{3,9,20,15,7};
//    vector<int> ino{1,2,3,4};
    vector<int> ino{9,3,15,20,7};
    auto a = solution.buildTree(pre,ino);
    ;
}


