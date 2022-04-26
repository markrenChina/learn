//
// Created by mark on 2022/4/20.
//
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

int main(){
    vector<string> vec{"the","quick","red","fox",
                       "jumps","over","the","slow",
                       "red","turtle"};
    for_each(vec.cbegin(),vec.cend(),[](const string &s){
        cout << s << " ";
    });
}