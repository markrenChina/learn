//
// Created by mark on 2021/6/17.
//

#ifndef DATASTRUCTURE_AVLTREE_HPP
#define DATASTRUCTURE_AVLTREE_HPP

#include <android/log.h>
#include <jni.h>

#define TAG "JNI_TAG"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)


namespace c9_3 {
    template<class K, class V>
    struct TreeNode {
        TreeNode<K, V> *left = nullptr;
        TreeNode<K, V> *right = nullptr;

        K key;
        V value;
        int height = 1;

        TreeNode(K key, V value) : key(key), value(value) {}

        TreeNode(TreeNode<K, V> *pNode) : left(pNode->left), right(pNode->right), key(pNode->key),
                                          value(pNode->value),height(pNode->height) {}
    };

    void visit(int key, int value) {
        LOGI("key = %d,value = %d", key, value);
    }

    template<class K, class V>
    class AVLTree {
        TreeNode<K, V> *root = nullptr;

        int count = 0;

    private:

        int getHeight(TreeNode<K,V> *pNode){
            return pNode ? pNode->height : 0;
        }

        int getDepthTree(TreeNode<K,V> *pNode) {
            if (pNode == nullptr) {
                return 0;
            }
            int leftDepth = getDepthTree(pNode->left);
            int rightDepth = getDepthTree(pNode->right);

            return std::max(leftDepth, rightDepth) + 1;
        }

        // 右旋
        TreeNode<K, V> *R_Rotation(TreeNode<K, V> *pNode){
            TreeNode<K,V> *originalLeft = pNode->left;
            TreeNode<K,V> *leftRight = originalLeft->right;
            /**
             * (根节点pNode成为自己左节点的右孩子)
             * root > left > left's left SO newRoot's right > newRoot > newRoot's left 成立
             * 因为以上公式，理论上不需要判断，完成后依然是一个平衡二叉树
             */
            originalLeft->right = pNode;
            /**
             * root > left.s right > left SO when root be newRoot's right,
             * left's right 不需要判断可以直接放在(newRoot's right)'s left
             * 因为root 的left 变成了newRoot SO root's now left == null 不存在覆盖问题
             */
             pNode->left = leftRight;
             // 更新高度
             pNode->height = std::max(getHeight(pNode->left),getHeight(pNode->right)) + 1;
             originalLeft->height = std::max(getHeight(originalLeft->left),getHeight(originalLeft->right))+1;
            return originalLeft;
        }

        // 左旋 跟右旋同理
        TreeNode<K, V> *L_Rotation(TreeNode<K, V> *pNode){
            TreeNode<K,V> *originalRight = pNode->right;
            TreeNode<K,V> *rightLeft = originalRight->left;
            originalRight->left = pNode;
            pNode->right = rightLeft;
            // 更新高度
            pNode->height = std::max(getHeight(pNode->left),getHeight(pNode->right)) + 1;
            originalRight->height = std::max(getHeight(originalRight->left),getHeight(originalRight->right))+1;
            return originalRight;
        }


        TreeNode<K, V> *addNode(TreeNode<K, V> *pNode, K key, V value) {
            if (pNode == nullptr) {
                count++;
                return new TreeNode<K, V>(key, value);
            }
            if (pNode->key > key) {
                pNode->left = addNode(pNode->left, key, value);

                if (getHeight(pNode->left) - getHeight(pNode->right) == 2) {
                    //调整
                    if (getHeight(pNode->left->right) > getHeight(pNode->left->left)){
                        //先左旋(调整左边的左边高度大于右边，使之后右旋之后成功一个合格的AVL)
                        pNode->left = L_Rotation(pNode->left);
                    }
                    pNode = R_Rotation(pNode);
                }

            } else if (pNode->key < key) {
                pNode->right = addNode(pNode->right, key, value);
                if (getHeight(pNode->right) - getHeight(pNode->left) == 2) {
                    //调整
                    if (getHeight(pNode->right->left) > getHeight(pNode->right->right)){
                        //先右旋
                        pNode->right = R_Rotation(pNode->right);
                    }
                    pNode = L_Rotation(pNode);
                }

            } else {
                pNode->value = value;
            }

            //更新二叉树的高度
            pNode->height = std::max(getHeight(pNode->left),getHeight(pNode->right))+1;
            return pNode;
        }

        TreeNode<K, V> *removeNode(TreeNode<K, V> *pNode, K key) {
            if (pNode == nullptr){
                return nullptr;
            }
            if (pNode->key > key) {
                pNode->left = removeNode(pNode->left, key);

                if (getHeight(pNode->right) - getHeight(pNode->left) == 2) {
                    //调整
                    if (getHeight(pNode->right->left) > getHeight(pNode->right->right)){
                        //先右旋
                        pNode->right = R_Rotation(pNode->right);
                    }
                    pNode = L_Rotation(pNode);
                }

            } else if (pNode->key < key) {
                pNode->right = removeNode(pNode->right, key);

                if (getHeight(pNode->left) - getHeight(pNode->right) == 2) {
                    //调整
                    if (getHeight(pNode->left->right) > getHeight(pNode->left->left)){
                        //先左旋(调整左边的左边高度大于右边，使之后右旋之后成功一个合格的AVL)
                        pNode->left = L_Rotation(pNode->left);
                    }
                    pNode = R_Rotation(pNode);
                }
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
                    if (getHeight(pNode->left) > getHeight(pNode->right)) {
                        //去左边找代替
                        TreeNode<K, V> *max = maximum(pNode->left);
                        TreeNode<K, V> *successor = new TreeNode<K, V>(max);
                        //保证移除子节点更新所有的高度
                        successor->left = removeNode(pNode->left,max->key);
                        count++;
                        successor->right = pNode->right;
                        delete pNode;
                        //更新高度
                        pNode = successor;
                    } else {
                        //去右边找最小值代替
                        TreeNode<K, V> *min = minimum(pNode->right);
                        TreeNode<K, V> *successor = new TreeNode<K, V>(min);
                        //保证移除子节点更新所有的高度
                        successor->right = removeNode(pNode->right,min->key);
                        count++;
                        successor->left = pNode->left;
                        delete pNode;
                        //更新高度
                        pNode = successor;
                    }
                }
            }

            pNode->height = std::max(getHeight(pNode->left),getHeight(pNode->right) +1);
            return pNode;
        }

        //查找当前树的最大值 一直往右找，找到空的前一个
        TreeNode<K, V> *maximum(TreeNode<K, V> *pNode) {
            if (pNode->right == nullptr) {
                return pNode;
            }
            return maximum(pNode->right);
        }

        //查找当前树的最小值 一直往左找，找到空的前一个
        TreeNode<K, V> *minimum(TreeNode<K, V> *pNode) {
            if (pNode->left == nullptr) {
                return pNode;
            }
            return minimum(pNode->left);
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
        AVLTree() {}

        ~AVLTree() {}

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

        //层序遍历
        void levelTraverse(void (*fun)(K, V)){
            if (root == nullptr) {
                return;
            }

            TreeNode<K,V> *node = root;
            std::queue<TreeNode<K,V>*> nodes;
            nodes.push(node);
            while (!nodes.empty()){
                node = nodes.front();
                fun(node->key,node->value);
                nodes.pop();
                if (node->left){
                    nodes.push(node->left);
                }

                if (node->right) {
                    nodes.push(node->right);
                }
            }
        }
    };

    template<class K, class V>
    void test_AVLTree() {
        AVLTree<int, int> *avl = new AVLTree<int, int>();
        avl->put(3, 3);
        avl->put(2, 2);
        avl->put(1, 1);

        avl->put(4, 4);
        avl->put(5, 5);

        avl->put(6, 6);
        avl->put(7, 7);
        avl->put(10, 10);
        avl->put(9, 9);
        avl->put(8, 8);

        //avl->remove(3);
        //avl->remove(1);

        avl->remove(4);
        avl->remove(5);
        avl->remove(2);
        avl->remove(10);
        avl->remove(6);

        avl->remove(100);

        avl->levelTraverse(visit);
    }
}

#endif //DATASTRUCTURE_AVLTREE_HPP
