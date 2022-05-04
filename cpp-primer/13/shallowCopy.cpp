//
// Created by mark on 2022/4/28.
//
#include <string>

class HasPtr {
public:
    //分配新的计数器，计1
    HasPtr(const std::string& s = std::string()):ps(new std::string(s))
    ,i(0),use(new std::size_t(1)){};

    HasPtr(const HasPtr& p) : ps(p.ps),i(p.i),use(p.use) { ++*use;}

    HasPtr& operator=(const HasPtr& );

    ~HasPtr();

private:
    std::string *ps;
    int i;
    std::size_t *use; //引用计数
};

HasPtr::~HasPtr() {
    if (--*use == 0){
        delete ps;
        delete use;
    }
}
//为了应付自赋值，先递增后递减
HasPtr& HasPtr::operator=(const HasPtr& rhs) {
    ++*rhs.use;   //递增右侧的引用计数
    if (--*use == 0){
        delete ps;
        delete use;
    }
    ps = rhs.ps;
    i = rhs.i;
    use = rhs.use;
    return *this;
}
