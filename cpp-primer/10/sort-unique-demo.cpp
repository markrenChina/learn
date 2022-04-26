//
// Created by mark on 2022/4/19.
//
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

int main(){
    vector<string> vec{"the","quick","red","fox",
                       "jumps","over","the","slow",
                       "red","turtle"};
    //按字典排序，以便查找重复单词
    sort(vec.begin(),vec.end());
    //unique重排，使得每个单词出现一次
    //排列在范围的前部，返回指向不重复区域之后一个位置的迭代器
    auto end_unique = unique(vec.begin(),vec.end());
    //使用向量操作erase删除重复单词
    vec.erase(end_unique,vec.end());
    for (const auto& e: vec) {
        //输出 fox jumps over quick red slow the turtle
        cout << e << " ";
    }
}