//
// Created by mark on 2022/5/11.
//
#include <iostream>
#include <regex>

using namespace std;


int main(){
    //查找不在字符c之后的字符串ei
    string pattern("[^c]ei");
    //我们需要包含pattern的整个单词
    pattern = "[[:alpha:]]*" + pattern +"[[:alpha:]]*";
    //构造一个用于查找模式的regex
    regex r(pattern);
    //定义一个对象保存搜索结果
    smatch results;
    //定义一个string 保存与模式匹配和不匹配的文本
    string test_str = "receipt freind theif receive";
    //用r在test_str中查找与pattern匹配的子串
    if (regex_search(test_str,results,r)){
        //打印匹配结果
        cout << results.str() << endl;
    }
}