//
// Created by mark on 2022/4/26.
//
#include <iostream>
#include <fstream>

#include "TextQuery.h"
#include "QueryResult.h"

using namespace std;


void runQueries(ifstream &infile) {
    //infile 是一个ifstream,指向我们要处理的文件
    TextQuery tq(infile);   //保存文件并建立查询map
    //与用户交互：提示用户输入要查询的单词，完成查询并打印结果
    while (true) {
        cout << "enter word to look for, or q to quit: ";
        string s;
        //若遇到文件尾或用户输入了'q'时循环终止
        if (!(cin >> s) || s == "q") break;
        //指向查询并打印结果
        print(cout, tq.query(s)) << endl;
    }
}


int main(int argc, char *argv[]) {
    ifstream ifs("./test.txt");
    runQueries(ifs);

    return 0;
}