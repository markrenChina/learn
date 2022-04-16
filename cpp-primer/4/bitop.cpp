//
// Created by mark on 2022/4/2.
//

#include <iostream>


int main(){
    using namespace std;
    unsigned char bits = 227;
    std::cout << (~bits) << std::endl;

    //将第27置1
    unsigned char count = 6;
    cout << (count | 0x08000000) << endl;

    cout << 0x08000000 << endl;

    //生成一个值只有27位是1
    unsigned long bit = 1UL << 27;
    cout << bit << endl;
    unsigned long count2 = 6;
    cout << (count2 | bit) << endl;
    cout << (count2 & (~bit)) << endl;
    cout << (count2 & bit) << endl;
}
