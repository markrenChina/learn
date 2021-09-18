//
// Created by Ushop on 2021/9/17.
//

#include <iostream>

int main() {
    using namespace std;

    int nights = 1001;
    int * pt = new int; //allocate space for an int
    *pt = 1001; //store a value there

    cout << "nights value = ";
    cout << nights << ": location " << &nights << endl;
    cout <<"int ";
    cout <<"Value = " << *pt << ": location = " << pt << endl;
    double * pd = new double ;// allocate space for an double
    *pd = 10000001.0;// store a double there

    cout <<"double ";
    cout << "Value = " << *pd << ": location = " << pd << endl;
    cout << "location of pointer pd: " << &pd << endl;
    cout << "Size of pt = " << sizeof(pt) ;
    cout << ": Size of *pt =" << sizeof(*pt) << endl;
    cout << "Size of pd = " << sizeof pd;
    cout << ": Size of *pd  = " << sizeof(*pd) << endl;
    return 0;
}