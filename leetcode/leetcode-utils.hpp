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

using namespace std;


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

#endif //LEETCODE_LEETCODE_UTILS_HPP
