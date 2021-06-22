//
// Created by mark on 2021/6/20.
//

#ifndef DATASTRUCTURE_MAP_HPP
#define DATASTRUCTURE_MAP_HPP

#include <android/log.h>
#include <jni.h>

#define TAG "JNI_TAG"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)


namespace c9_4 {
    typedef bool rb_color;
#define red true
#define black false


    void visit(int key, int value) {
        LOGI("key = %d,value = %d", key, value);
    }

    template<class K, class V>
    class map {
    private:
        struct TreeNode;
        int count;
        TreeNode *root;

        TreeNode *findTree(K key){
            TreeNode* node = root;
            while (node) {
                if (key == node->key) {
                    return node;
                } else if (key > node->key){
                    node = node->right;
                } else {
                    node = node->left;
                }
            }
            return nullptr;
        }

        //处理黑色
        void solveLostBlack(TreeNode* pNode){
            while (pNode != root && pNode->color == black) {
                if (left(parent(pNode)) == pNode) {
                    //当前节点是父亲节点的左节点
                    TreeNode* sib = brother(pNode);
                    if (getColor(sib) == red) {
                        //想办法把兄弟节点变成黑色
                        setColor(sib,black);
                        setColor(parent(pNode),red);
                        L_Rotation(parent(pNode));
                        sib = brother(pNode);
                    }

                    if (getColor(left(sib)) == black && getColor(right(sib)) == black) {
                        setColor(sib,red);
                        pNode = parent(pNode);
                    } else {
                        if (getColor(right(sib)) == black) {
                            setColor(sib,red);
                            setColor(left(sib),black);
                            R_Rotation(sib);
                            sib = brother(pNode);
                        }
                        setColor(sib,getColor(parent(pNode)));
                        setColor(parent(pNode),black);
                        setColor(right(sib),black);
                        L_Rotation(parent(pNode));
                        pNode = root;
                    }
                } else {
                    //当前节点是父亲节点的右节点
                    TreeNode* sib = brother(pNode);
                    if (getColor(sib) == red) {
                        //想办法把兄弟节点变成黑色
                        setColor(sib,black);
                        setColor(parent(pNode),red);
                        R_Rotation(parent(pNode));
                        sib = brother(pNode);
                    }

                    if (getColor(left(sib)) == black && getColor(right(sib)) == black) {
                        setColor(sib,red);
                        pNode = parent(pNode);
                    } else {
                        if (getColor(left(sib)) == black) {
                            setColor(sib,red);
                            setColor(right(sib),black);
                            L_Rotation(sib);
                            sib = brother(pNode);
                        }
                        setColor(sib,getColor(parent(pNode)));
                        setColor(parent(pNode),black);
                        setColor(left(sib),black);
                        R_Rotation(parent(pNode));
                        pNode = root;
                    }
                }
            }
            //把遇到的红色节点染黑
            pNode->color = black;
        }
    public:
        map() : root(nullptr) {
            count = 0;
        }

        ~map() {}

        struct iterator;

        TreeNode *parent(TreeNode *pNode) {
            return pNode ? pNode->parent : nullptr;
        }

        TreeNode *left(TreeNode *pNode) {
            return pNode ? pNode->left : nullptr;
        }

        TreeNode *right(TreeNode *pNode) {
            return pNode ? pNode->right : nullptr;
        }

        TreeNode *brother(TreeNode *pNode) {
            TreeNode * p = parent(pNode);
            return left(p) == pNode ? right(p) : left(p);
        }

        bool getColor(TreeNode *pNode) {
            return pNode ? pNode->color : black;
        }

        void setColor(TreeNode *pNode, rb_color color) {
            if (pNode) {
                pNode->color = color;
            }
        }

        TreeNode *L_Rotation(TreeNode *pNode) {
            //左旋
            TreeNode *right = pNode->right;
            pNode->right = right->left;
            right->left = pNode;

            //调整原pNode父亲 指向新的儿子
            if (pNode->parent == nullptr) {
                root = right;
            } else if (pNode->parent->left == pNode) {
                pNode->parent->left = right;
            } else {
                pNode->parent->right = right;
            }

            //调整所有节点的父亲
            right->parent = pNode->parent;
            if (pNode->right) {
                pNode->right->parent = pNode;
            }
            pNode->parent = right;
            return right;
        }

        TreeNode *R_Rotation(TreeNode *pNode) {
            //左旋
            TreeNode *left = pNode->left;
            pNode->left = left->right;
            left->right = pNode;

            //调整原pNode父亲 指向新的儿子
            if (pNode->parent == nullptr) {
                root = left;
            } else if (pNode->parent->left == pNode) {
                pNode->parent->left = left;
            } else {
                pNode->parent->right = left;
            }

            //调整所有节点的父亲
            left->parent = pNode->parent;
            if (pNode->left) {
                pNode->left->parent = pNode;
            }
            pNode->parent = left;
            return left;
        }

        //解决双红
        void solveDoubleRed(TreeNode *pNode) {
            while (pNode->parent && pNode->parent->color == red) {
                if (getColor(brother(pNode->parent)) == red) {
                    setColor(pNode->parent, black);
                    setColor(brother(pNode->parent), black);
                    setColor(parent(pNode->parent), red);

                    pNode = parent(pNode->parent);
                } else { // 情况3
                    if (left(parent(parent(pNode))) == parent(pNode) ){
                        if (right(parent(pNode)) == pNode) {
                            pNode = pNode->parent;
                            L_Rotation(pNode);
                        }
                        setColor(pNode->parent, black);
                        setColor(parent(pNode->parent), red);
                        R_Rotation(parent(pNode->parent));
                    }else {
                        if (left(parent(pNode)) == pNode) {
                            pNode = pNode->parent;
                            R_Rotation(pNode);
                        }
                        setColor(pNode->parent, black);
                        setColor(parent(pNode->parent), red);
                        L_Rotation(parent(pNode->parent));
                    }
                }
            }

            root->color = black;
        }

        iterator insert(K key, V value) {
            if (root == nullptr) {
                root = new TreeNode(nullptr, nullptr, nullptr, key, value, black);
                count = 1;
                return iterator(root);
            }

            //最好先插入红色节点，红色不会违反性质5，但是可能会违反性质4
            TreeNode *node = root;
            TreeNode *parent = nullptr;
            do {
                parent = node;
                if (key == node->key) {
                    node->value = value;
                    return iterator(node);
                } else if (key > node->key) {
                    node = node->right;
                } else {
                    node = node->left;
                }
            } while (node);

            TreeNode *new_node = new TreeNode(nullptr, nullptr, parent, key, value, red);

            if (key > parent->key) {
                parent->right = new_node;
            } else {
                parent->left = new_node;
            }

            solveDoubleRed(new_node);
            count++;
            return iterator(new_node);
        }

        bool remove(K key) {
            TreeNode* current = findTree(key);
            if (current == nullptr){
                return false;
            }
            if (current->left != nullptr && current->right != nullptr){
                TreeNode* successor = current ->successor();
                current->key = successor->key;
                current->value = successor->value;
                //successor 肯定左节点为空
                current = successor;
            }
            TreeNode *replace = current->left?current->left :current->right;
            if (replace!= nullptr){
                if (current->parent){
                    root = replace;
                }else if (current->parent->left == current) {
                    current->parent->left = replace;
                } else {
                    current->parent->right = replace;
                }
                replace->parent = current->parent;
                if (current->color == black) {
                    solveLostBlack(replace);
                }
                delete current;
            } else if (current->parent == nullptr){
                delete root;
                root = nullptr;
            } else {
                if (current->color == black) {
                    solveLostBlack(current);
                }
                if (current->parent) {
                    if (current->parent->left == current){
                        current->parent->left = nullptr;
                    } else {
                        current->parent->right= nullptr;
                    }
                }
                delete current;
            }


            count--;
            return true;
        }

        int size() {
            return count;
        }

        bool empty() {
            return count == 0;
        }

        //层序遍历
        void levelTraverse(void (*fun)(K, V)) {
            if (root == nullptr) {
                return;
            }

            TreeNode *node = root;
            std::queue<TreeNode *> nodes;
            nodes.push(node);
            while (!nodes.empty()) {
                node = nodes.front();
                fun(node->key, node->value);
                nodes.pop();
                if (node->left) {
                    nodes.push(node->left);
                }

                if (node->right) {
                    nodes.push(node->right);
                }
            }
        }

    };

    template<class K, class V>
    struct map<K, V>::TreeNode {
    public:
        TreeNode *left;
        TreeNode *right;
        TreeNode *parent;
        K key;
        V value;
        //颜色
        rb_color color;
    public:
        TreeNode(TreeNode *left, TreeNode *right, TreeNode *parent, K key, V value, rb_color color)
                : left(left), right(right), parent(parent), key(key), value(value), color(color) {}

        //找前驱
        TreeNode *precursor() {
            if (left) {
                TreeNode *node = left;
                while (node->right) {
                    node = node->right;
                }
                return node;
            } else {
                TreeNode *node = this;
                //找root的左孩子
                while (node->parent && node->parent->right == node) {
                    node = node->parent;
                }
                return node->parent;
            }
        }

        TreeNode *successor() {
            if (right) {
                TreeNode *node = right;
                while (node->left) {
                    node = node->left;
                }
                return node;
            } else {
                TreeNode *node = this;
                //找root的右孩子
                while (node->parent && node->parent->left == node) {
                    node = node->parent;
                }
                return node->parent;
            }
        }

    };

    template<class K, class V>
    struct map<K, V>::iterator {
    private:
        TreeNode *node;
    public:
        iterator(TreeNode *node) : node(node) {}

        iterator &operator--() {//找前驱
            node = node->precursor();
            return *this;
        }

        iterator &operator++() {//找后继
            node = node->successor();
            return *this;
        }

        V operator*() {
            return node->value;
        }
    };

    template<class K, class V>
    void test_readBlackTree() {
        map<int, int> *rbtMap = new map<int, int>();
        rbtMap->insert(3, 3);
        rbtMap->insert(2, 2);
        rbtMap->insert(1, 1);

        rbtMap->insert(4, 4);
        rbtMap->insert(5, 5);

        rbtMap->insert(6, 6);
        rbtMap->insert(7, 7);
        rbtMap->insert(10, 10);
        rbtMap->insert(9, 9);
        rbtMap->insert(8, 8);

        rbtMap->remove(2);
        //avl->remove(1);

        //avl->remove(4);
        //avl->remove(5);
        //avl->remove(2);
        //avl->remove(10);
        //avl->remove(6);

        //avl->remove(100);

        rbtMap->levelTraverse(visit);
    }
}


#endif //DATASTRUCTURE_MAP_HPP
