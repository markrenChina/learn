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
    string::size_type sz = 4;
    auto it = find_if(vec.begin(), vec.end(),[sz](const string&a){
        return a.size()>sz;
    });

    while (it != vec.end()){
        cout << *it << " ";
        it++;
        it = find_if(it, vec.end(),[sz](const string&a){
            return a.size()>sz;
        });
    }
}