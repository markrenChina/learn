//
// Created by mark on 2022/4/19.
//
#include <vector>
#include <iostream>


using namespace std;

int main(){
    int vec[]{1,2,3,4};
    int vec2[sizeof(vec) / sizeof(*vec)];
    auto ret = copy(begin(vec), end(vec),vec2);
    for (auto e: vec2) {
        //42
        cout << e << " ";
    }
    cout << endl;
}