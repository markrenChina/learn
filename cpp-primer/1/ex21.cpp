//
// Created by mark on 2022/3/26.
//

#include <iostream>
#include "Sales_item.h"


int main(){
    using namespace std;
    Sales_item salesItem[2];
    for (auto  &e : salesItem) {
        cin >> e;
    }
    //todo 应该检查isbn是否相同
    clog << "======================";
    cout << salesItem[0] + salesItem[1] << endl;
    return 0;
}