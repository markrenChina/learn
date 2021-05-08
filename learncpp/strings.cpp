//
// Created by Ushop on 2021/4/19.
//

#include <iostream>
#include <cstring>

int main12() {
    using namespace std;
    const int Size = 15;
    char name1[Size];
    char name2[Size] = "C++owboy";

   /* cout << "Howdy! I'm " << name2;
    cout << "! What's your name?\n";
    cin >> name1;
    cout << "Well, " << name1 << ", your name has ";
    cout << strlen(name1) << " letters and is stored\n";
    cout << "in an array of " << sizeof(name1) << " bytes.\n";
    cout << "Your initial is " << name1[0] <<".\n";
    name2[3] ='\0';
    cout << "Here are the first 3 characters of my name: ";
    cout << name2 << endl;*/

    //insert1 cin输入空格会导致前一个被写入name，后面的被写入dessert
    const int ArSize = 20;
    /*char name[ArSize];
    char dessert[ArSize];

    cout << "\n insert1 \n";

    cout << "Enter your name: \n";
    cin >> name;
    cout << "Enter you favorite dessert:\n";
    cin >> dessert;
    cout << "I have some delicious " << dessert;
    cout << " for you, " << name << ".\n";*/

    //insert2 cin输入空格会导致前一个被写入name，后面的被写入dessert
    // cin >> 前面的输入会影响后面的
    /*char name_2[ArSize];
    char dessert2[ArSize];

    cout << "\n insert2 \n";

    cout << "Enter your name: \n";
    cin.getline(name_2,ArSize);
    cout << "Enter you favorite dessert:\n";
    cin.getline(dessert2,ArSize);
    cout << "I have some delicious " << dessert2;
    cout << " for you, " << name_2 << ".\n";*/

    cout << "\n insert3 \n";

    char name_3[ArSize];
    char dessert3[ArSize];

    cout << "Enter your name:\n";
    cin.get(name_3,ArSize).get();
    cout << "Enter your favorite dessert:\n";
    cin.get(dessert3,ArSize).get();
    cout << "I have some delicious " << dessert3;
    cout << " for you, " << name_3 << ".\n";

    return 0;
}

