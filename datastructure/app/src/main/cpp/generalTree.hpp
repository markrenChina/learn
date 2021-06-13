//
// Created by mark on 2021/6/13.
//

#ifndef DATASTRUCTURE_GENERALTREE_HPP
#define DATASTRUCTURE_GENERALTREE_HPP

namespace c9 {

#define TREE_SIZE 100

    typedef struct CTNode {
        int child;//孩子的下标
        struct CTNode *next = nullptr;
    } *CTNodePtr;//CTreePtr是个指针

    template<class Element>
    struct CTree {
        Element data; //存放的数据
        int parent; //双亲节点的角标
        CTNodePtr firstChild;//链表形式的第一个孩子节点指针
    };

    template<class Element>
    struct Tree {
        CTree<Element> nodes[TREE_SIZE];//树存放的数组内存
        int root;//根节点位置
        int size;//节点个数
    };

    void test_generalTree(){
        Tree<char> tree;
        tree.root = 0;
        CTree<char> first;
        tree.nodes[0] = first;
    }

}

#endif //DATASTRUCTURE_GENERALTREE_HPP
