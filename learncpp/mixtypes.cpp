//
// Created by Ushop on 2021/9/17.
//
#include <iostream>

struct antarctica_years_end {
    int year;
    /* some really interesting data, etc */
};

int main() {
    using namespace std;
    antarctica_years_end s01, s02, s03;
    s01.year = 1998;
    antarctica_years_end *pa = &s02;
    pa->year = 1999;
    antarctica_years_end trio[3]; // array of 3 structures
    trio[0].year = 2003;
    cout << trio -> year << endl; // print 2003
    const antarctica_years_end * arp[3] = { &s01, &s02, &s03};
    cout << arp[1] -> year << endl;  //print 1999
    const antarctica_years_end ** ppa = arp;
    auto ppb = arp; //c++11 automatic type deduction
    //or else use const antarctica_years_end **ppa = arp;
    cout << (*ppa) -> year << endl;  //print 1998
    cout << (*(ppb + 1)) ->year << endl; //print 1999
    /*int j = 0;
    for (int i = 0; i < 10;  cout << i++ << " ", cout << ++i << endl) {
    }
    cout <<endl;*/
    return 0;

}