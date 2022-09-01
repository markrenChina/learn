//
// Created by Ushop on 2022/8/16.
//
#include "leetcode-utils.hpp"

class OrderedStream {
private:
    int ptr = 0;
    vector<string> vec;
public:
    OrderedStream(int n) {
        vec.resize(n);
    }

    vector<string> insert(int idKey, string value) {
        vec[idKey-1] = value;
        vector<string> res;
        while(ptr < vec.size() && !vec[ptr].empty()){
            res.emplace_back(vec[ptr]);
            ptr++;
        }
        return res;
    }
};

int main(){
    OrderedStream stream(5);
    auto c = stream.insert(3,"cc");
    auto a = stream.insert(1,"aa");
}