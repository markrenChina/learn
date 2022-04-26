//
// Created by mark on 2022/4/19.
//

#include <vector>
#include <iostream>
#include <numeric>


using namespace std;

int main(){
    vector<int> vec{1,2,3,4,5,6};
    int sum = accumulate(vec.cbegin(),vec.cend(),0);
    cout << sum <<endl;
    vector<string> vector{"a","b","c"};
    //const char* 没有 +
    string str = accumulate(vector.cbegin(), vector.cend(),string(""));
    cout << str;
}