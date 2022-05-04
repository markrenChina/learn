
//
// Created by mark on 2022/4/29.
//
#include <string>


class HasPtr {
public:
    friend void swap(HasPtr&, HasPtr&);
    HasPtr(const std::string& s = std::string()):ps(new std::string(s)),i(0){};

    HasPtr(const HasPtr& p) : ps(new std::string(*p.ps)),i(p.i) {}

    HasPtr& operator=(const HasPtr);

    ~HasPtr() { delete ps; }

private:
    std::string *ps;
    int i;
};


inline
void swap(HasPtr &lhs, HasPtr &rhs) {
    using std::swap;
    swap(lhs.ps,rhs.ps);
    swap(lhs.i,rhs.i);
}


//注意，按值传递
HasPtr &HasPtr::operator=(const HasPtr rhs) {
    swap(*this, rhs);
    return *this;
}