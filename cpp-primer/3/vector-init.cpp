//
// Created by mark on 2022/3/30.
//

#include <iostream>
#include <vector>
#include "../1/Sales_item.h"
using namespace std;

int main(){
    vector<Sales_item> v1{ 4,{"111"}};
    vector<Sales_item> v2(v1);
    v2.at(0) = Sales_item("2222");
    cout << v1.at(0) << "\n";
    cout << v1.at(1) << "\n";
    cout << v2.at(0) << "\n";

    v1 = v2;
    cout << v1.size() << "\n";
    cout << v1.at(0) << "\n";
    cout << v1.at(1) << "\n";

    cout << "=======================" << "\n";
    v2.clear();
    cout << v2.size() << "\n";
    v2 = {{"3333"},{"3333333"}};
    cout << v2.size() << "\n";
}