//
// Created by mark on 2022/4/26.
//

#ifndef CPP_PRIMER_TEXTQUERY_H
#define CPP_PRIMER_TEXTQUERY_H

#include <vector>
#include <map>
#include <set>
#include <iostream>

#include "QueryResult.h"

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
