//
// Created by Ushop on 2021/4/10.
//
#include <iostream>

int main3() {
    using namespace std;

    int carrots;

    cout << "How many carrots to you have?"<<endl;
    cin >> carrots;//c++ input
    cout << "Here are two more. ";
    carrots = carrots + 2;
    //the next line concatenates output
    cout << "Now you have " << carrots << " carrots. "<< endl;
    return 0;
}