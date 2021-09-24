//
// Created by mark on 2021/9/24.
//
#include <iostream>
#include <stdexcept>

void mPrintf(const char * s){
    while (*s) {
        if (*s=='%' && *(++s) != '%'){
            throw std::runtime_error("invalid format string:missing arguments");
        }
        std::cout << *s++;
    }
}

template<typename T,typename... Args>
void mPrintf(const char* s,T value, Args... args){
    while (*s) {
        if (*s == '%' && *(++s)!='%'){
            std::cout << value;
            printf(++s,args...);
            return;
        }
        std::cout << *s++;
    }
    throw std::runtime_error("extra arguments provided to printf");
}

int main(){
    int *pi = new int ;
    mPrintf("%d %s %p %f\n",15,"This is Ace.",pi,3.141592653);
}