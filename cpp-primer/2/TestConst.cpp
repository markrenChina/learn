//
// Created by mark on 2022/3/26.
//

#include <iostream>


int main(){
    int i = 42;
    int& r1 = i;
    const int& r2 = i;
    r1 = 0;
    //r2 = 1; //error
    std::clog << r2;
    return 0;
}