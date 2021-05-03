//
// Created by mark on 2021/5/3.
//

#ifndef LEARNJNI_ARRAYLIST_H
#define LEARNJNI_ARRAYLIST_H

template <class E>
class ArrayList{
private:
    //长度
    int size;
    //内容
    E* array = nullptr;
    //角标
    int endPoint;
    
    void ensureCapacityInternal(int);
    
    void grow(int);
public:
    ArrayList();

    ArrayList(int size);
    
    void add(E e);

    E remove(int index);

    E get(int index);

    ~ArrayList();
    
    int getSize();
};

#endif //LEARNJNI_ARRAYLIST_H
