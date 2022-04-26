//
// Created by mark on 2022/4/19.
//
#include <vector>
#include <iostream>
#include <numeric>


using namespace std;

int main(){
    vector<int> vec{1,2,3,4,5,6};
    vector<int> vec2{1,2,3,4,5,6};
    bool res = equal(vec.cbegin(),vec.cend(),vec2.cbegin());

    cout << res;
    return 0;
}