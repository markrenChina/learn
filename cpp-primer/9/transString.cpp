//
// Created by mark on 2022/4/19.
//
#include <iostream>
#include <string>

using namespace std;

int main(){
    int i = 42;
    string s = to_string(i);  //将整数i转换成字符串42
    double d = stod(s);       //将字符串s转换成浮点数
    cout << d <<endl;
}