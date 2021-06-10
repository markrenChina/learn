//
// Created by mark on 2021/6/4.
//

#ifndef DATASTRUCTURE_LINKSTACK_HPP
#define DATASTRUCTURE_LINKSTACK_HPP
#include <assert.h>

namespace c9 {

    template<class E>
    class Node {
        Node *next;
        E value;
    public:
        Node(E value,Node *next){
            this->next = next;
            this->value = value;
        }
        ~Node() {

        }
    };
    template<class E>
    class LinkStack {
    private:
        c9::Node<E>* head = nullptr;
        c9::Node<E>* top = nullptr;
        int size;
        E node(int index);
    public:
        LinkStack();

        ~LinkStack();

        bool isEmpty();

        //弹栈
        E pop();

        //获取不弹
        E peek();

        //压栈
        void push(E e);


    };

    template<class E>
    LinkStack<E>::LinkStack() {

    }

    template<class E>
    LinkStack<E>::~LinkStack() {
        Node<E>* h = head;
        while (head) {
            Node<E>* next = h->next;
            delete h;
            head = next;
        }
        head = nullptr;
    }

    template<class E>
    E LinkStack<E>::pop() {
        E val = top->value;
        delete top;
        top = node(size--);
        if (size ==0){
            head = nullptr;
            top = nullptr;
        }
        return val;
    }

    template <class E>
    bool LinkStack<E>::isEmpty() {
        return top == -1;
    }

    template<class E>
    E LinkStack<E>::peek() {
        return top;
    }

    template<class E>
    void LinkStack<E>::push(E e) {
        Node<E> *new_node = new Node<E>(e, nullptr);
        if (top){
            top->next = new_node;
        }else {
            head = new_node;
        }
        top = new_node;
        size++;
    }

    template<class E>
    E LinkStack<E>::node(int index) {
        Node<E> *h = head;
        for (int  i =0; i< index;++i){
            h = h->next;
        }
        return h;
    }

}


#endif //DATASTRUCTURE_LINKSTACK_HPP
