//
// Created by mark on 2022/5/8.
//
#include <iostream>
#include <type_traits>

int main(){
    std::string s1("hi"),s2;
    std::string& s3 = s1;
    std::string s4 = static_cast<std::string&&>(s3);
    std::cout << "s2 = "<< s4 <<std::endl;
    std::cout << "s1 = "<< s1 <<std::endl;
    s2 = std::move(s1);
    std::cout << "s2 = "<< s2 <<std::endl;
    std::cout << "s1 = "<< s1 <<std::endl;
}