//
// Created by mark on 2022/5/4.
//

#include "TextQuery.h"
#include <memory>
#include <sstream>
#include <string>
#include <fstream>

TextQuery::TextQuery(std::ifstream& ifs):file(new std::vector<std::string>) {
    using namespace std;
    string text;
    while (std::getline(ifs, text)) {  //对文件的每一行
        file->push_back(text);   //保存此行文本
        int n = file->size() - 1;  //当前行号
        istringstream line(text); //将行文本分解为单词
        string word;
        while (line >> word) {  //对行中每个单词
            //如果单词不在wm中，以之为下标在wm中添加一项
            auto &lines = wm[word];  //lines 是一个shared_ptr
            if (!lines) {
                //在我们第一次遇到这个单词时，此指针为空
                lines = std::make_shared<set<line_no>>(); //分配一个新的set
            }
            lines->insert(n);   //将此行号插入set中
        }
    }
}

QueryResult TextQuery::query(const std::string &sought) const {
    //如果未找到sought,我们将返回一个指向此set的指针
    static std::shared_ptr<std::set<TextQuery::line_no>> nodata(new std::set<TextQuery::line_no>);
    //使用find而不是下标运算符来查找单词，避免单词添加到wm中
    auto loc = wm.find(sought);
    if (loc == wm.end()){
        return {sought, nodata, file};
    } else {
        return {sought,loc->second,file};
    }
}