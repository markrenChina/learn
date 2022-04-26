//
// Created by mark on 2022/4/19.
//
#include <vector>
#include <iostream>
#include <numeric>

using namespace std;


int main(){
    vector<int> vec{1,2,3,4,5,6};
    fill(vec.begin(), vec.end(),0);
    for (auto e: vec) {
        cout << e << " ";
    }
    return 0;
}