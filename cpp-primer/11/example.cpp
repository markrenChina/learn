//
// Created by mark on 2022/4/23.
//
#include <map>
#include <set>
#include <iostream>

using namespace std;

/**
 * 示例，忽略常见单词
 * @return
 */
int main() {

    map<string, size_t> word_count;
    set<string> exclude = {"The", "But", "And"};

    string word;
    while (cin >> word && word != "Exit") {
        //只统计不在exclude中的单词
        if (exclude.find(word) == exclude.end()) {
            ++word_count[word];
            cout << word << " count :" << word_count[word] << endl;
        } else {
            cout << word << " is exclude world" << endl;
        }
    }
    cout << "=======================================" << endl;
    auto map_it = word_count.cbegin();
    while (map_it != word_count.cend()) {
        cout << map_it->first << " occurs "
             << map_it->second << " times" << endl;
        ++map_it;
    }
}
