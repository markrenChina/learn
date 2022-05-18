//
// Created by mark on 2022/5/11.
//
#include <iostream>
#include <bitset>

#define OP(init,fun) \
    {                \
    std::bitset<sizeof(init)-1> bit(init); \
    std::cout << "bit(\"" << init << "\") => "<< #fun <<" : " << bit.fun << std::endl; \
}

int main(){
    OP("1000",any())
    OP("0000",any())
    OP("1111",all())
    OP("1000",all())
    OP("1000",none())
    OP("0000",none())
    OP("0000",count())
    OP("1111",count())
    OP("0011",size())
    OP("11",size())
    OP("0001",test(0))
    OP("0000",test(0))
    OP("0000",set())
    OP("0000",set(1))
    OP("0111",reset())
    OP("1111",reset(1))
    OP("0111",flip())
    OP("1111",flip(1)) //等价~bit[1]
    OP("1111",to_ulong())
    OP("1111",to_ullong())
    OP("1010",to_string())
}