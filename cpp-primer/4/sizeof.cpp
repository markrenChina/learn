//
// Created by mark on 2022/4/3.
//

#include <iostream>

int main(){
    using namespace std;

    char c = 'c';
    cout << sizeof c << endl;   //1
    char& d =c;
    cout << sizeof d << endl;   //==size of c =1
    char* e = &c;
    cout << sizeof e << endl;   //指针大小跟机器有关  8
    char* f = nullptr;
    cout << sizeof *f << endl; //1
    char g[10];
    cout << sizeof g << endl;  //10
    //返回数组元素个数,类型为常量表达式，可以用来新建数组
    constexpr size_t size = (sizeof g) / (sizeof *g );
    cout << "count of g : " << size <<endl;
    char h[size]; //编译通过

    //string s = "abcdefg";
    //cout << sizeof (s) << endl; //vc 无法编译，linux下g++返回32
}