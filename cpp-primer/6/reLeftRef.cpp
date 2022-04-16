//
// Created by mark on 2022/4/4.
//
#include <iostream>

using namespace std;

char& get_val(string &str, string::size_type ix){
    return str[ix]; //假定索引有效
}
int main(){
    string s("a value");
    cout << s << endl;         //输出 a value
    get_val(s,0) = 'A';        //将s[0]的值改为A
    cout << s << endl;         //输出 A value
    return 0;
}