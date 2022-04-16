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
    for (auto  &e : salesItem) {
        cout << e << endl;
    }

    return 0;
}