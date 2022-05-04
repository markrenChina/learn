//
// Created by mark on 2022/5/4.
//

#ifndef CPP_PRIMER_TEXTQUERY_H
#define CPP_PRIMER_TEXTQUERY_H


#include <vector>
#include <map>
#include <set>
#include <iostream>

#include "QueryResult.h"

/**
 * 该类读入给定的文件并构建一个查找图。这个类包含一个query操作。
 * 它接受一个string实参，返回一个QueryResult对象
 * 该QueryResult对象表示string出现的行
 */
class TextQuery {
public:
    using line_no = std::vector<std::string>::size_type;

    TextQuery(std::ifstream&);

    QueryResult query(const std::string&) const;

private:

    std::shared_ptr<std::vector<std::string>> file; //输入文件
    //每个单词到它所在行号的集合的映射
    std::map<std::string,std::shared_ptr<std::set<line_no> > > wm;
};

#endif //CPP_PRIMER_TEXTQUERY_H
