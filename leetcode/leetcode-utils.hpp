//
// Created by Ushop on 2022/7/13.
//

#ifndef LEETCODE_LEETCODE_UTILS_HPP
#define LEETCODE_LEETCODE_UTILS_HPP

#include <iostream>
#include <vector>
#include <stack>
#include <map>
#include <set>
#include <unordered_set>
#include <unordered_map>
#include <queue>
#include <algorithm>
#include <cstring>
#include <cstdint>
#include <sstream>
#include <array>
#include <functional>

using namespace std;

template<typename TreeNode>
int getBinaryTreeDepth(TreeNode* node){
    if(!node){
        return 0;
    }
    return ::max(getBinaryTreeDepth<TreeNode>(node->left), getBinaryTreeDepth(node->right))+1;
}

template<typename TreeNode,typename T>
void _serializeBinaryTree(TreeNode* node,T nullTag,vector<T>& result,size_t pos = 0){
    if ( pos >= result.size()){
        return;
    }
    if (!node){
        result[pos] = nullTag;
    }else {
        result[pos] = node->val;
        _serializeBinaryTree<TreeNode,T>(node->left,nullTag,result,pos * 2 + 1);
        _serializeBinaryTree<TreeNode,T>(node->right,nullTag,result,pos * 2 + 2);
    }
}

template<typename TreeNode,typename T>
vector<T> SerializeBinaryTree(TreeNode* root,T nullTag,size_t size = 0){
    if (!size){
        size = pow(2,getBinaryTreeDepth(root)) - 1;
    }
    vector<T> result;
    result.resize(size);
    _serializeBinaryTree(root,nullTag,result);
    return result;
}


template<typename TreeNode,typename T>
TreeNode* DeserializeBinaryTree(vector<T> vec,T nullTag,size_t pos = 0){
    if ( pos >= vec.size() || vec[pos] == nullTag){
        return nullptr;
    }
    auto* tmp = new TreeNode(vec[pos]);
    tmp->left = DeserializeBinaryTree<TreeNode,T>(vec,nullTag,pos * 2 + 1);
    tmp->right = DeserializeBinaryTree<TreeNode,T>(vec,nullTag,pos * 2 + 2);
    return tmp;
}

template<typename C>
void printContainer(C vec){
    for_each(vec.begin(), vec.end(),[](auto i){
        cout << i << " " ;
    });
    cout << endl;
}

#endif //LEETCODE_LEETCODE_UTILS_HPP
