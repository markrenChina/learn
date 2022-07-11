//
// Created by Ushop on 2022/6/22.
//
#include "CurlClient.h"
#include <iostream>

int main(){
    CCurlClient::init();
    CCurlClient client;
    std::string response;
    if(!client.get("http://192.168.1.253:20103/sys/getTime", nullptr,response)){
        std::cout << "fail" << std::endl;
    }
    std::cout << response << std::endl;
    CCurlClient::uninit();
}