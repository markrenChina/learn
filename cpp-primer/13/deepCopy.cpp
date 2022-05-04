//
// Created by mark on 2022/4/28.
//
#include <string>

class HasPtr {
public:
    HasPtr(const std::string& s = std::string()):ps(new std::string(s)),i(0){};

    HasPtr(const HasPtr& p) : ps(new std::string(*p.ps)),i(p.i) {}

    HasPtr& operator=(const HasPtr &);

    ~HasPtr() { delete ps; }

private:
    std::string *ps;
    int i;
};

HasPtr &HasPtr::operator=(const HasPtr& rhs) {
    //如果不用临时对象，先delete直接赋值给ps，假设传入的this，会导致未定义
    auto newp = new std::string(*rhs.ps);
    delete ps;
    ps = newp;
    i = rhs.i;
    return *this;
}


