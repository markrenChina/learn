//
// Created by mark on 2022/5/22.
//
#include <iostream>

class Base {
    friend bool operator==(const Base&, const Base&);
public:
    //Base的接口成员
protected:
    virtual bool equal(const Base&) const;
    //Base 的数据成员和其他用于实现的成员
};

class Derived: public Base{
public:
    //Derived 的其他接口成员
protected:
    bool equal(const Base&) const override;
    //Derived 的数据成员和其他用于实现的成员
};

bool Base::equal(const Base &rhs) const {
    //执行比较Base对象的操作
    return 0==0;
}

bool Derived::equal(const Base &rhs) const {
    //因为==已经验证类型所以转换过程不会抛出异常
    auto r = dynamic_cast<const Derived&>(rhs);
    //执行比较两个Derived对象的操作并返回结果
    return 0==0;
}

bool operator==(const Base &lhs, const Base &rhs) {
    return typeid(lhs) == typeid(rhs) && lhs.equal(rhs);
}



int main(){

}