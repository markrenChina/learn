//
// Created by mark on 2021/5/3.
//

#include <cstdlib>
#include <string.h>
#include "ArrayList.h"

template<class E>
ArrayList<E>::ArrayList() {

}

template<class E>
ArrayList<E>::ArrayList(int size) {
    if(size <= 0) {
        return;
    }
    this->size = size;
    this->array = (E*)malloc(sizeof(E) * size);
}

template<class E>
void ArrayList<E>::add(E e) {
    ensureCapacityInternal(endPoint+1);
    this->array[endPoint++] = e;
}

template<class E>
E ArrayList<E>::remove(int index) {
    return nullptr;
}

template<class E>
E ArrayList<E>::get(int index) {
    return this->array[index];
}

template<class E>
ArrayList<E>::~ArrayList() {
    if(this->array) {
        free(this->array);
        this->array = nullptr;
    }
}

template<class E>
int ArrayList<E>::getSize() {
    return this -> EndPoint;
}

template<class E>
void ArrayList<E>::ensureCapacityInternal(int capacity) {
    if (this ->array == nullptr) {
        capacity = 10;
    }
    
    if (capacity - size > 0) {
        grow(capacity);
    }
}

template<class E>
void ArrayList<E>::grow(int capacity) {
    int new_size = size + (size >> 1);
    if (capacity - new_size > 0) {
        new_size = capacity;
    }
    auto *new_arr = (E*)malloc(sizeof(E)*new_size);
    if (this -> array) {
        memcpy(new_arr,this -> array,sizeof(E)*endPoint);
        free(this->array);
    }
    
    this->array = new_arr;
    this->size = new_size;
}
