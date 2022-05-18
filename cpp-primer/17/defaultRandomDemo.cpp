//
// Created by mark on 2022/5/14.
//
#include <iostream>
#include <random>
#include <ctime>


int main(){
    std::cout << time(0) << std::endl;
    std::default_random_engine e;  //生成随机无符号数
    std::cout << e.min() << " ~ " << e.max() << std::endl;

    for (int i = 0; i < 10; ++i) {
        //e() "调用"对象来生成一个随机数
        std::cout << e() << " ";
    }
    std::cout<<  std::endl << "--------------" << std::endl;
    //生成0到9之间（包含）均匀分布的随机数
    std::uniform_int_distribution<unsigned > u(0,9);
    for (int i = 0; i < 10; ++i) {
        //e() "调用"对象来生成一个随机数
        std::cout << u(e) << " ";
    }

    std::cout<<  std::endl << "--------------" << std::endl;
    //生成0到1之间无符号的随机数
    std::uniform_real_distribution<double > u2(0,1);
    for (int i = 0; i < 10; ++i) {
        //e() "调用"对象来生成一个随机数
        std::cout << u2(e) << " ";
    }
}