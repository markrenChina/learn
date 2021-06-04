//
// Created by mark on 2021/6/3.
//

#ifndef DATASTRUCTURE_ARRAYSTACK_HPP
#define DATASTRUCTURE_ARRAYSTACK_HPP


#include <malloc.h>
#include <assert.h>

namespace c9 {

    template<class E>
    class ArrayStack {
    private:
        //栈顶指针
        int top = -1;
        E *array = nullptr;
        int size = 10;

        void growArray();
    public:
        ArrayStack();

        ~ArrayStack();

        bool isEmpty();

        //弹栈
        E pop();

        //获取不弹
        E peek();

        //压栈
        void push(E e);

    };

    template<class E>
    ArrayStack<E>::ArrayStack() {
        array = (E*)malloc(size * sizeof(E));
    }

    template<class E>
    ArrayStack<E>::~ArrayStack() {
        delete [] array ;
    }

    template<class E>
    E ArrayStack<E>::pop() {
        assert(top > -1);
        return array[top--];
    }

    template <class E>
    bool ArrayStack<E>::isEmpty() {
        return top == -1;
    }

    template<class E>
    E ArrayStack<E>::peek() {
        return array[top];
    }

    template<class E>
    void ArrayStack<E>::push(E e) {
        if (top +1 == size) {
            //扩容
            growArray();
        }
        array[++top] = e;
    }

    template<class E>
    void ArrayStack<E>::growArray() {
       size += size>>1;
       array = (E*)realloc(array,sizeof(E) * size);
    }
}/**/



#endif //DATASTRUCTURE_ARRAYSTACK_HPP
