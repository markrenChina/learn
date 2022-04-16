//
// Created by mark on 2022/4/5.
//
#include <iostream>

using namespace std;


void print(const int ia[], size_t size){
#ifndef NDEBUG
    //__func__ 是编译器定义的一个局部静态变量，用于存放函数的名字
    cerr << __func__ << ": array size is " << size << endl;
#endif
}

void log(){
#ifndef NDEBUG
    cerr << "Error: " << __FILE__
    << " : in function " << __func__
    << " at line " << __LINE__ << endl
    << "             Compiled on " << __DATE__
    << " at " << __TIME__ << endl;
#endif
}

int main(){

    print({},100);
    log();
}