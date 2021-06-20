//
// Created by mark on 2021/6/17.
//

#ifndef DATASTRUCTURE_BINARYSEARCHTREE_HPP
#define DATASTRUCTURE_BINARYSEARCHTREE_HPP

#include <android/log.h>

#define TAG "JNI_TAG"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)


namespace c9_2 {
    template<class K, class V>
    struct TreeNode {
        TreeNode<K, V> *left = nullptr;
        TreeNode<K, V> *right = nullptr;

        K key;
        V value;

        TreeNode(K key, V value) : key(key), value(value) {}

        TreeNode(TreeNode<K, V> *pNode) : left(pNode->left), right(pNode->right), key(pNode->key),
                                          value(pNode->value) {}
    };

    void visit(int key, int value) {
        LOGI("key = %d,value = %d", key, value);
    }

    template<class K, class V>
    class binarySearchTree {
        TreeNode<K, V> *root = nullptr;

        int count = 0;

    private:
        TreeNode<K, V> *addNode(TreeNode<K, V> *pNode, K key, V value) {
            if (pNode == nullptr) {
                count++;
                return new TreeNode<K, V>(key, value);
            }
            if (pNode->key > key) {
                pNode->left = addNode(pNode->left, key, value);
            } else if (pNode->key < key) {
                pNode->right = addNode(pNode->right, key, value);
            } else {
                pNode->value = value;
            }
            return pNode;
        }

        TreeNode<K, V> *removeNode(TreeNode<K, V> *pNode, K key) {
            if (pNode == nullptr){
                return nullptr;
            }
            if (pNode->key > key) {
                pNode->left = removeNode(pNode->left, key);
            } else if (pNode->key < key) {
                pNode->right = removeNode(pNode->right, key);
            } else {
                count--;
                if (pNode->left == pNode->right) {
                    delete pNode;
                    return nullptr;
                } else if (pNode->left == nullptr) {
                    TreeNode<K, V> *right = pNode->right;
                    delete pNode;
                    return right;
                } else if (pNode->right == nullptr) {
                    //TreeNode<K, V> *right = pNode->right;
                    TreeNode<K, V> *left = pNode->left;
                    delete pNode;
                    return left;
                } else {
                    //左右都不为空， 从左边找一个最大值，或者从右边找一个最小值
                    TreeNode<K, V> *successor = new TreeNode<K, V>(maximum(pNode->left));
                    //移除找到的节点跟父节点的链接
                    successor->left = deleteMax(pNode->left);
                    count++;
                    successor->right = pNode->right;
                    delete pNode;
                    return successor;
                }
            }
            return pNode;
        }

        //查找当前树的最大值 一直往右找，找到空的前一个
        TreeNode<K, V> *maximum(TreeNode<K, V> *pNode) {
            if (pNode->right == nullptr) {
                return pNode;
            }
            return maximum(pNode->right);
        }

        TreeNode<K, V> *deleteMax(TreeNode<K, V> *pNode) {
            if (pNode->right == nullptr) {
                TreeNode<K, V> *left = pNode->left;
                delete pNode;
                count--;
                return left;
            }
            pNode->right = deleteMax(pNode->right);
            return pNode;
        }

        void inOrderTraverse(TreeNode<K, V> *pNode, void (*fun)(K, V)) {
            if (pNode == nullptr) {
                return;
            }
            inOrderTraverse(pNode->left, fun);
            fun(pNode->key, pNode->value);
            inOrderTraverse(pNode->right, fun);
        }

    public:
        binarySearchTree() {}

        ~binarySearchTree() {}

        void put(K key, V value) {
            root = addNode(root, key, value);
        }

        V *get(K key) {
            TreeNode<K, V> *node = root;
            while (node) {
                if (node->key == key) {
                    return &node->value;
                } else if (node->key > key) {
                    node = node->left;
                } else {
                    node = node->right;
                }
            }
            return nullptr;
        }

        int size() {
            return count;
        }

        void remove(K key) {
            root = removeNode(root, key);
        }

        bool contains(K key) {
            TreeNode<K, V> *node = root;
            while (node) {
                if (node->key == key) {
                    return node->value;
                } else if (node->key > key) {
                    node = node->left;
                } else {
                    node = node->right;
                }
            }
            return false;
        }

        //中序遍历
        void inOrderTraverse(void (*fun)(K, V)) {
            inOrderTraverse(root, fun);
        }
    };

    template<class K, class V>
    void test_binarySearchTree() {
        binarySearchTree<int, int> *bst = new binarySearchTree<int, int>();
        bst->put(1, 1);
        bst->put(5, 5);
        bst->put(8, 8);
        bst->put(-5, -5);
        bst->put(3, 3);
        bst->put(6, 6);
        bst->put(-6, -6);
        bst->inOrderTraverse(visit);
        LOGI("%d", bst->contains(1));
        LOGI("%d", bst->contains(100));
        LOGI("%d", *bst->get(-5));
        LOGI("%s", "=====================");
        bst->remove(1);
        bst->inOrderTraverse(visit);
    }
}

#endif //DATASTRUCTURE_BINARYSEARCHTREE_HPP
