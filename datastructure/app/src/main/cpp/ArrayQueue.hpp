//
// Created by mark on 2021/6/8.
//

#ifndef DATASTRUCTURE_ARRAYQUEUE_HPP
#define DATASTRUCTURE_ARRAYQUEUE_HPP

#include <malloc.h>

namespace c9 {
    template<class E>
    class ArrayQueue {
    private:
        int size = 0;
        int head = 0;
        int tail = 0;
        E *array;
        void growArray();
        void copyElement(E *src, int sPo, E *dest, int dPo, int len);
    public:
        ArrayQueue();

        ArrayQueue(int size);

        ~ArrayQueue();

        bool isEmpty();

        E pop();

        void push(E e);

        E peek();


    };

    template<class E>
    ArrayQueue<E>::ArrayQueue():ArrayQueue(8) {
        //free(array) ;
        delete [] (array);
    }

    template<class E>
    ArrayQueue<E>::ArrayQueue(int size) {
        //size 乱传保证长度是2的幂次
        int real_size = size;
        if (real_size >= 4) {
            real_size |= real_size >> 1;
            real_size |= real_size >> 2;
            real_size |= real_size >> 4;
            real_size |= real_size >> 8;
            real_size |= real_size >> 16;
            real_size++;
        }
        this->size = real_size;
        array = (E *) std::malloc(sizeof(E) * real_size);
    }
    template<class E>
    ArrayQueue<E>::~ArrayQueue<E>() {

    }

    template<class E>
    void ArrayQueue<E>::push(E e) {
        head = (head - 1) & (size - 1);
        array[head] = e;
        if (tail == head) {
            //todo 扩容 （双端数组）
            growArray();
        }
    }

    template<class E>
    E ArrayQueue<E>::peek() {
        return array[(tail - 1) & (size - 1)];
    }

    template<class E>
    E ArrayQueue<E>::pop() {
        tail = (tail - 1) & (size - 1);
        return array[tail];
    }

    template<class E>
    bool ArrayQueue<E>::isEmpty(){
        return head==tail;
    }

    template<class E>
    void ArrayQueue<E>::growArray() {
        int new_size = size <<1;
        E *new_array = (E*)std::malloc(sizeof(E) * new_size);

        int r = size -tail;
        copyElement(array,tail,new_array,0,r);
        copyElement(array,0,new_array,r,tail);
        free(array);
        head = 0;
        tail = size;
        size = new_size;
        array = new_array;
    }

    template<class E>
    void ArrayQueue<E>::copyElement(E *src, int sPo, E *dest, int dPo, int len) {
        for (int i = 0; i < len; ++i) {
            dest[dPo + i] = src[sPo + i];
        }
    }
};
#endif //DATASTRUCTURE_ARRAYQUEUE_HPP
