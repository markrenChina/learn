//
// Created by mark on 2022/3/26.
//
#include <iostream>

int main(){
    int in[10];
    int total = 0;
    std::cout << "Enter 10 numbers " <<std::endl;
    for (int & i : in) {
        std::cin >> i;
        total += i;
    }
    std::cout << " sum = " << total << std::endl;
    return 0;
}
