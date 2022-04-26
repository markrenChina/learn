//
// Created by mark on 2022/4/19.
//
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

int main(){
    vector<int> vec{1,2,3,4,5,6};
    replace(vec.begin(),vec.end(),2,42);
    for (auto e: vec) {
        cout << e << " ";
    }
}