//
// Created by Ushop on 2021/5/8.
//
#include <iostream>

struct inflateble
{
    char name[20];
    float volume;
    double price;
};

int main () {
    using namespace std;

    inflateble guest = {
            "Glorious Gloria",
            1.88,
            29.99
    };
    inflateble pal = {
            "Audacious Arthur",
            3.12,
            32.99
    };

    cout << "Expand your guest list with " <<guest.name;
    cout << " and" <<pal.name <<"!\n";
    cout << "You can have both for $";
    cout << guest.price + pal.price << "!\n";

    return 0;
}

