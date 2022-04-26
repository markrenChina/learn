//
// Created by mark on 2022/4/20.
//
#include <iostream>


using namespace std;

//值捕获
void fcn1(){
    size_t v1 = 42;
    //v1被拷贝到f的可调用对象
    auto f = [v1]{return v1;};
    v1 = 0;
    cout << f() << endl; //输出42
}

//引用捕获
void fcn2(){
    size_t v1 = 42;
    auto f = [&v1]{return v1;};
    v1 = 0;
    cout << f() << endl; //输出0
}

//可变lambda
void fcn3(){
    size_t v1 = 42;
    auto f = [v1]()mutable {return ++v1;};
    v1 = 0;
    cout << f() << endl; //输出43
}

int main(){
    fcn1();
    fcn2();
    fcn3();
}