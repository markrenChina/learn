//
// Created by mark on 2021/5/23.
//

#ifndef DATASTRUCTURE_LINKEDLIST_HPP
#define DATASTRUCTURE_LINKEDLIST_HPP

#include <assert.h>

//节点
template<class E>
struct Node {
    Node<E> *prev;
    Node<E> *next;
    E value;

public:
    Node(Node<E> *prev, E value, Node<E> *next) {
        this->value = value;
        this->next = next;
        this->prev = prev;
    };
};


template<class E>
class LinkedList {
    //头节点指针
    Node<E> *first= nullptr;
    //尾节点指针
    Node<E> *last= nullptr;
    //链表长度
    int size = 0;

public:
    /**
    * 添加元素到前面
    * @param e
    */
    void push(E e);

    /**
     * 添加元素到后面
     */
    void add(E e);

    /**
     * 获取指定坐标的值
     * @param index
     * @return value
     */
    E get(int index);

    /**
     * 获取指定坐标，node节点
     */
    Node<E>* node(int index);

    /**
     * 获取size；
     */
     int getSize();

     /**
      * 插入
      * @param index 目标下标
      */
      void insert(int index,E value);

      void remove(int index);

    ~LinkedList();
};

template<class E>
void LinkedList<E>::push(E e) {
    Node<E> *f = first;
    auto *new_node = new Node<E>(nullptr, e, first);
    first = new_node;
    if (f) {
        f->prev = new_node;
    } else {
        last = new_node;
    }
    this->size++;
}

template<class E>
void LinkedList<E>::add(E e) {
    Node<E> *l = last;
    auto *new_node = new Node<E>(last, e, nullptr);
    last = new_node;
    if (l) {
        l->next = new_node;
    } else {
        first = new_node;
    }
    this->size++;
}

template<class E>
Node<E>* LinkedList<E>::node(int index) {
    assert(index >= 0 && index < size);
    int m_size = size >> 1;
    if (index < m_size) {
        Node<E>* x = first;
        for (int i = 0; i < index; i++) {
            x = x->next;
        }
        return x;
    } else {
        Node<E>* x = last;
        for (int i = size - 1; i > index; i--) {
            x = x->prev;
        }
        return x;
    }
}

template<class E>
int LinkedList<E>::getSize() {
    return this->size;
}

template<class E>
LinkedList<E>::~LinkedList<E>() {
    Node<E> *f = first;
    while (f) {
        Node<E> *next = f->next;
        delete f;
        f = next;
    }

    first = nullptr;
    last = nullptr;
}

template<class E>
E LinkedList<E>::get(int index) {
    Node<E>* node1 = node(index);
    return node1->value;
}

template<class E>
void LinkedList<E>::insert(int index, E value) {
    if (index == 0){
        push(value);
        return;
    } else if (index == size){
        add(value);
        return;
    }
    assert(index >= 1 && index < size);
    Node<E> *prev = node(index -1);
    Node<E> *next = node(index);
    auto *new_node = new Node<E>(prev,value,next);
    prev->next = new_node;
    next->prev = new_node;
    this->size++;
}

template<class E>
void LinkedList<E>::remove(int index) {
    assert(index >= 0 && index < size);
    if (index == 0 ){
        Node<E> *f = first;
        this->first = f->next;
        this->size--;
        if (size == 0) {
            this->last = nullptr;
        }
        delete f;
        return;
    } else if (index == (size-1)){
        Node<E> *l = last;
        last->prev->next = nullptr;
        last = l->prev;
        delete l;
        this->size--;
        return;
    }else {
        Node<E> *n = node(index);
        Node<E> *prev = n->prev;
        Node<E> *next = n->next;
        prev->next = next;
        next->prev = prev;
        delete n;
        this->size--;
    }
}

#endif //DATASTRUCTURE_LINKEDLIST_HPP

