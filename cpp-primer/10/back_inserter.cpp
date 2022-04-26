//
// Created by mark on 2022/4/19.
//
#include <vector>
#include <iostream>
#include <iterator>


using namespace std;

int main(){
    vector<int> vec;
    auto it = back_inserter(vec); //赋值运算符会调用push_back
    *it = 42;
    for (auto e: vec) {
        //42
        cout << e << " ";
    }
    cout << endl;
    fill_n(back_inserter(vec),10,0);
    for (auto e: vec) {
        //42 0 0 0 0 0 0 0 0 0 0
        cout << e << " ";
    }
}