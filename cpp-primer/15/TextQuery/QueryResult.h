//
// Created by mark on 2022/5/4.
//

#ifndef CPP_PRIMER_QUERYRESULT_H
#define CPP_PRIMER_QUERYRESULT_H


#include <iostream>
#include <set>
#include <vector>

class TextQuery;

//该类保存一个query操作的结果
class QueryResult {
    using line_no = std::vector<std::string>::size_type;
    using line_iterator = std::set<line_no>::iterator;
    friend std::ostream &print(std::ostream &, const QueryResult &);

public:
    QueryResult(std::string sought,
                std::shared_ptr<std::set<line_no>> lines,
                std::shared_ptr<std::vector<std::string>> file);

    line_iterator begin();
    line_iterator end();

    const std::shared_ptr<std::vector<std::string>> &getFile() const;

private:
    std::string sought;   //查询单词
    std::shared_ptr<std::set<line_no>> lines;  //出现的行号
    std::shared_ptr<std::vector<std::string>> file;       //输入文件
};



#endif //CPP_PRIMER_QUERYRESULT_H
