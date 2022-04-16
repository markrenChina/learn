//
// Created by mark on 2022/4/3.
//

#include <iostream>


int testThrow(){
    throw std::runtime_error("test throw");
    return 0;
}

int main(){
    try {
        testThrow();
    }catch (std::runtime_error error){
        std::cout << error.what();
    }
}