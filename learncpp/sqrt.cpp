//
// Created by Ushop on 2021/4/10.
//
#include <iostream>
#include <cmath>

int main4() {
    using namespace std;

    double area;
    cout << "Enter the floor area,in square feet, of your home: ";
    cin >> area;
    double side;
    side = sqrt(area);
    cout << "That's the equivalent of a square " << side
    <<" free to the side."<<endl;
    cout << "How fascinating" << endl;
    return 0;
}