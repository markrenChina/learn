//
// Created by mark on 2021/6/13.
//

#ifndef DATASTRUCTURE_BINARYTREE_HPP
#define DATASTRUCTURE_BINARYTREE_HPP

#include <android/log.h>
#include <queue>
#include <algorithm>
#include <cmath>

#define TAG "JNI_TAG"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)

namespace c9 {
    template<class T>
    class TreeNode {
    public:
        TreeNode(T data) {
            this->data = data;
        }

        T data;//数据
        TreeNode *left = nullptr; //左孩子
        TreeNode *right = nullptr;
    };

    void visitChar(char data) {
        LOGI("%c", data);
    }

    int max(int i);

    template<class T>
    int getDepthTree(TreeNode<T> *pNode) {
        if (pNode == nullptr) {
            return 0;
        }
        int leftDepth = getDepthTree(pNode->left);
        int rightDepth = getDepthTree(pNode->right);

        return std::max(leftDepth, rightDepth) + 1;
    }

    /**
     * 判断是否是否平衡二叉树
     * @tparam T
     * @param pNode
     * @return
     */
    template<class T>
    bool isBalanceTree(TreeNode<T> *pNode) {
        if (pNode == nullptr) {
            return true;
        }
        //左右子树的高度差不会超过1
        int leftDepth = getDepthTree(pNode->left);
        int rightDepth = getDepthTree(pNode->right);

        return std::abs(leftDepth - rightDepth) <= 1
        && isBalanceTree(pNode->left)
        && isBalanceTree(pNode->right);
    }

    

    /**
     * 前序遍历
     */
    template<class T>
    void preOrderTraverse(TreeNode<T> *pNode, void visit(T)) {
        if (pNode == nullptr) {
            return;
        }
        //首先根节点
        visit(pNode->data);
        //然后左节点
        preOrderTraverse(pNode->left, visit);
        //然后右节点
        preOrderTraverse(pNode->right, visit);
    }

    /**
     * 中序遍历
     */
    template<class T>
    void infixOrderTraverse(TreeNode<T> *pNode, void visit(T)) {
        if (pNode == nullptr) {
            return;
        }
        //首先左节点
        infixOrderTraverse(pNode->left, visit);
        //然后根节点
        visit(pNode->data);
        //然后右节点
        infixOrderTraverse(pNode->right, visit);
    }

    /**
     * 后序遍历
     */
    template<class T>
    void epilogueOrderTraverse(TreeNode<T> *pNode, void visit(T)) {
        if (pNode == nullptr) {
            return;
        }
        //首先左节点
        epilogueOrderTraverse(pNode->left, visit);
        //然后右节点
        epilogueOrderTraverse(pNode->right, visit);
        //然后根节点
        visit(pNode->data);
    }

    /**
     * 层序遍历
     */
    template<class T>
    void levelOrderTraverse(TreeNode<T> *pNode, void visit(T)) {
        if (pNode == nullptr) {
            return;
        }
        std::queue < TreeNode<T> * > nodeQ;
        nodeQ.push(pNode);
        while (!nodeQ.empty()) {
            TreeNode<T> *front = nodeQ.front();
            nodeQ.pop();
            visit(front->data);
            if (front->left) {
                nodeQ.push(front->left);
            }
            if (front->right) {
                nodeQ.push(front->right);
            }
        }
    }

    void test_binaryTree() {
        TreeNode<char> *A = new TreeNode<char>('A');
        TreeNode<char> *B = new TreeNode<char>('B');
        TreeNode<char> *C = new TreeNode<char>('C');
        TreeNode<char> *D = new TreeNode<char>('D');
        TreeNode<char> *E = new TreeNode<char>('E');
        TreeNode<char> *F = new TreeNode<char>('F');
        TreeNode<char> *G = new TreeNode<char>('G');
        TreeNode<char> *H = new TreeNode<char>('H');

        A->left = B;
        A->right = C;

        B->left = D;
        B->right = E;

        C->right = F;

        //前序遍历： 首先访问根节点，然后访问左节点，最后访问右节点
        //ABDECF
        //preOrderTraverse(A,visitChar);
        //中序遍历： 首先访问左节点，然后访问根节点，然后访问右节点
        //DBEACF
        //infixOrderTraverse(A,visitChar);
        //后序遍历： 首先访问左节点，然后访问右节点，然后访问根节点
        //DEBFCA
        //epilogueOrderTraverse(A,visitChar);
        //层序遍历： 每一层
        //levelOrderTraverse(A,visitChar);

        int depth = getDepthTree(A);
        LOGI("depth = %d", depth);
    };
}

#endif //DATASTRUCTURE_BINARYTREE_HPP
