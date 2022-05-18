//
// Created by Ushop on 2021/4/12.
//
#include <iostream>

int main() {
    using namespace std;
    int chest = 42;
    int waist = 0x42;
    int inseam = 042;

    cout << "Monsieur cuts a striking figure!\n";
    cout << "chest = " << chest << " (42 in decimal)\n";
    cout << "waist = " << waist << " (0x42 in hex)\n";
    cout << "inseam = " << inseam << " (042 in octal)\n";

    int chest2 = 42;
    int waist2 = chest2;
    int inseam2 = waist2;
    cout << showbase;
    cout << "Monsieur cuts a striking figure!" << endl;
    cout << "chest2 = " << chest2 << " (decimal for 42)" << endl;
    cout << hex;
    cout << "waist2 = " << waist2 << " (hexadecimal for 42)" << endl;
    cout << oct;
    cout << "inseam2 = " << inseam2 << " (octal for 42 )" << endl;

    return 0;
}
