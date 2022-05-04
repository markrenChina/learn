//
// Created by mark on 2022/5/4.
//
#include <fstream>
#include <iostream>

#include "Query.h"

int main(){
    std::ifstream ifs("./test.txt");
    TextQuery tq(ifs);
    Query q = Query("return");

    print(std::cout ,q.eval(tq));
    //std::cout << q.rep() ;
}
