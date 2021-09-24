//
// Created by mark on 2021/9/24.
//

#include <algorithm>
#include <iostream>

/**
 * 利用Variadic Templates
 */
template<typename T>
T maximum(T n){
    return n;
}

template<typename T,typename... Args>
T maximum(T n,Args... args){
    return std::max(n, maximum(args...));
}

int main(){
    std::cout << maximum(57,48,60,100,200,18) << std::endl;
}