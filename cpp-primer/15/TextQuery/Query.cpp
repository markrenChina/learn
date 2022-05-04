//
// Created by mark on 2022/5/4.
//

#include "Query.h"

inline
Query::Query(const std::string &s): q(new WordQuery(s)) {

}

inline
Query operator&(const Query& lhs, const Query& rhs) {
    return std::shared_ptr<Query_base>(new AndQuery(lhs,rhs));
}


Query operator|(const Query &lhs, const Query &rhs) {
    return std::shared_ptr<Query_base>(new OrQuery(lhs, rhs));
}

inline
Query operator~(const Query &operand) {
    return std::shared_ptr<Query_base>(new NotQuery(operand));
}