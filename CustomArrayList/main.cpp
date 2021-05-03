//
//  main.cpp
//  CustomArrayList
//
//  Created by mark on 2021/5/3.
//

#include <iostream>
#include "ArrayList.h"

using namespace std;
int main(int argc, const char * argv[]) {
    ArrayList<int> *list = new ArrayList<int>();
    cout << "Hello, World!\n";
    for (int i =0; i < 20; ++i) {
        list -> add(i);
    }
    
    for (int i = 0; i < list->getSize(); ++i) {
        cout << list->get(i)<< " ";
    }
    cout << endl;
    
    delete list;
    // insert code here...
    cout << "Hello, World!\n";
    return 0;
}
