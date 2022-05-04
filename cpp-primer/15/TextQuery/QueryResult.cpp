//
// Created by mark on 2022/5/4.
//

#include "QueryResult.h"

#include <utility>

QueryResult::QueryResult(std::string sought, std::shared_ptr<std::set<line_no>> lines,
                         std::shared_ptr<std::vector<std::string>> file) : sought(std::move(sought)),
                                                                           lines(std::move(lines)),
                                                                           file(std::move(file)) {}

std::string make_plural(size_t ctr, const std::string &word, const std::string &ending) {
    return (ctr > 1) ? word + ending : word;
}

std::ostream &print(std::ostream &os, const QueryResult &qr) {
    //如果找到了单词，打印出现次数和所有出现的位置
    os << qr.sought << " occurs " << qr.lines->size() << " "
       << make_plural(qr.lines->size(), "time", "s") << std::endl;
    //打印单词出现的每一行
    for (auto num: *qr.lines) {
        //避免行号从0开始给用户带来的困惑
        os << "\t(lines" << num + 1 << ")"
           << *(qr.file->begin() + num) << std::endl;

    }
    return os;
}

QueryResult::line_iterator QueryResult::begin() {
    return lines->begin();
}

QueryResult::line_iterator QueryResult::end() {
    return lines->end();
}

const std::shared_ptr<std::vector<std::string>> &QueryResult::getFile() const {
    return file;
}
