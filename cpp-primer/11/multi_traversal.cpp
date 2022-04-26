//
// Created by mark on 2022/4/23.
//
#include <map>
#include <iostream>

using namespace std;

int main() {
    multimap<string, string> authors{
            {"foo",          "bar"},
            {"markrenChina", "hello"},
            {"foo",          "bar2"},
            {"markrenChina", "world"},
            {"hello",        "world"}
    };
    string search_item("markrenChina");
    auto entries = authors.count(search_item);
    auto iter = authors.find(search_item);
    while (entries) {
        cout << iter->second << endl;
        ++iter;
        --entries;
    }

    for (auto beg = authors.lower_bound(search_item),
                 end = authors.upper_bound(search_item);
         beg != end; ++beg) {
        cout << beg->second << endl;
    }

    for (auto pos = authors.equal_range(search_item);
         pos.first != pos.second; ++pos.first) {
        cout << pos.first->second << endl;
    }
}