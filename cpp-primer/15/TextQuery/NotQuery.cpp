//
// Created by mark on 2022/5/4.
//

#include "Query.h"



//返回运算对象的结果set中不存在的行
QueryResult NotQuery::eval(const TextQuery& text) const {

    //通过Query运算对象对eval进行虚调用
    auto result = query.eval(text);
    //开始时结果set为空
    auto ret_lines = std::make_shared<std::set<line_no>>();
    //我们必须在运算对象出现的所有行中进行迭代
    auto beg = result.begin(),end = result.end();
    //对于输入文件的每一行，如果该行不在result当中，则将其添加到ret_lines
    auto sz = result.getFile()->size();
    for (size_t n = 0; n != sz; ++n) {
        //如果我们还没有处理完result的所有行
        //检查当前行是否存在
        if (beg == end || *beg !=n){
            ret_lines->insert(n);
        } else if (beg != end) {
            ++beg;
        }
    }
    return {rep(),ret_lines,result.getFile()};
}
