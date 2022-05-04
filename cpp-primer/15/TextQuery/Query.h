//
// Created by mark on 2022/5/4.
//

#ifndef CPP_PRIMER_QUERY_H
#define CPP_PRIMER_QUERY_H

#include <ostream>

#include "QueryResult.h"
#include "TextQuery.h"

/**
 * 查询类的基础抽象
 */
class Query_base {
    //所有多Query_base的使用都需要通过Query对象
    friend class Query;
protected:
    using line_no = TextQuery::line_no;  //用于eval函数
    virtual ~Query_base() = default;
private:
    //eval返回与当前Query匹配的QueryResult
    virtual QueryResult eval(const TextQuery& ) const = 0;
    //rep 是表示查询一个string
    virtual std::string rep() const = 0;
};

/**
 * 接口类，负责隐藏整个继承体系，指向Query_base对象
 */
class Query {
    //这些运算符需要访问接受shared_ptr的构造函数，而该函数是私有的
    friend Query operator~(const Query &);
    friend Query operator|(const Query&,const Query&);
    friend Query operator&(const Query&,const Query&);

public:
    Query(const std::string&);    //构建一个新的WordQuery
    //接口函数：调用对应的Query_base操作
    QueryResult eval(const TextQuery &t) const { return q->eval(t);}
    std::string rep() const { return q->rep(); }

private:
    Query(std::shared_ptr<Query_base> query) : q(query) {}
    std::shared_ptr<Query_base> q;

};

inline
std::ostream &operator<<(std::ostream &os, const Query &query) {
    //Query::rep 通过它的Query_base指针对rep()进行了虚调用
    return os << query.rep();
}

/**
 * 用于查找一个给定的单词
 */
class WordQuery : public Query_base{
    friend class Query;            //Query使用WordQuery构造函数
    WordQuery(const std::string &s):query_word(s) {}
    //具体的类：wordQuery将定义所有继承而来的纯虚函数
    QueryResult eval(const TextQuery& textQuery) const override
    { return textQuery.query(query_word); };

    std::string rep() const override
    { return query_word;};

    std::string query_word;        //要查找的单词
};

/**
 * 查询结果是Query运算对象没有出现的行的集合
 */
class NotQuery : public Query_base{
    friend Query operator~(const Query&);
    NotQuery(const Query& q):query(q){}
    //具体的类：NotQuery将定义所有继承而来的纯虚函数
    std::string rep() const { return "~(" + query.rep() + ")";}
    QueryResult eval(const TextQuery&) const;

    Query query;
};

/**
 * 抽象基类用于表示含有两个运算对象的查询
 */
class BinaryQuery : public Query_base{
protected:
    BinaryQuery(const Query& l,const Query& r,std::string s):lhs(l),rhs(r),opSym(s){}
    //抽象类：BinaryQuery不定义eval
    std::string rep() const { return "(" + lhs.rep() + " " + opSym + " " + rhs.rep() + ")";}

    Query lhs, rhs;  //左侧与右侧运输对象
    std::string opSym;  //运算符的名字
};

/**
 * 返回它的两个运算对象分别出现的行的交集
 */
class AndQuery : public BinaryQuery {
    friend Query operator&(const Query&, const Query&);
    AndQuery(const Query &left, const Query& right): BinaryQuery(left,right,"&"){}

    //具体的类：AndQuery继承了rep并且定义了其他纯虚函数
    QueryResult eval(const TextQuery&) const;
};

/**
 * 返回它的两个运算对象分布出现的行的并集
 */
class OrQuery: public BinaryQuery{
    friend Query operator|(const Query&,const Query&);
    OrQuery(const Query &left, const Query& right): BinaryQuery(left, right,"|"){}

    QueryResult eval(const TextQuery&) const;
};


#endif //CPP_PRIMER_QUERY_H
