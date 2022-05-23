//
// Created by mark on 2022/5/19.
//
#include <iostream>
#include <string>

namespace a1{

    class Query{
    public:
        Query(const std::string &name) : name(name) {}

        const std::string &getName() const {
            return name;
        }
    private:
        std::string name;
    };
}

namespace a2{
    class Query{
    public:
        Query(const std::string &name) : name(name) {}

        const std::string &getName() const {
            return name;
        }
    private:
        std::string name;
    };
}



int main(){
    //a1::Query q = a1::Query("a1");
    a2::Query q = a2::Query("a2");
    std::cout << q.getName() << std::endl;
}