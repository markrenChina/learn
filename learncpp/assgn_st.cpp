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

/*int main() {
    using namespace std;
    inflateble bouquet = {
            "sunflowers",
            0.20,
            12.49
    };

    inflateble choice;
    cout << "bouquet: " << bouquet.name << " for $";
    cout << bouquet.price << endl;

    choice = bouquet;
    cout << "choice" << choice.name << " for $";
    cout << choice.price << endl;

    return 0;
}*/

/*
int main() {
    using namespace std;

    inflateble guests[2] = {
            { "Bambi",0.5,21.99 },
            { "Godzilla",2000,565.99 }
    };

    cout << "The guests" << guests[0].name << " and " << guests[1].name
    << "\nhave a combined volume of "
    << guests[0].volume + guests[1].volume << " cubic feet." << endl;

    return 0;
}
//位字段，适用于寄存器
struct torgle_register {
    unsigned int SN : 4;
    unsigned int : 4;
    bool goodIn : 1;
    bool goodTorgle : 1;
};
//共用体
union one4all {
    int int_val;
    long long_val;
    double double_val;
};*/
