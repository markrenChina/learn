//
// Created by Ushop on 2021/9/28.
//
#include <winsock2.h>
#include <cstdio>

//链接Windows的socket库
#pragma comment(lib, "ws2_32.lib")

int main(int argc, char *argv[]) {
    //注意：由于goto语句不能跳过变量定义，所以在这里提前定义下文需要用到的变量
    SOCKET socketSrv;
    SOCKADDR_IN addrSrv;
    SOCKADDR_IN addrClient;
    int len = sizeof(SOCKADDR);
    char msg[] = "HelloWorld";

    //初始化socket库
    WORD mVersionRequested = MAKEWORD(2, 2);
    WSADATA wsadata;
    int err = WSAStartup(mVersionRequested, &wsadata);
    if (err != 0) return 1;
    if (LOBYTE(wsadata.wVersion) != 2 || HIBYTE(wsadata.wVersion) != 2) {
        //WSACleanup 清理socket库
        goto cleanup2;
    }

    //创建用于监听的socket
    socketSrv = socket(AF_INET, SOCK_STREAM, 0);
    if (socketSrv == -1) {
        goto cleanup2;
    }


    addrSrv.sin_addr.S_un.S_addr = htonl(INADDR_ANY);
    //#define AF_INET  internetwork: UDP, TCP, etc.
    addrSrv.sin_family = AF_INET;
    addrSrv.sin_port = htons(6000);
    //绑定socket，监听 6000端口
    if (bind(socketSrv, (SOCKADDR *) &addrSrv, sizeof(SOCKADDR)) == -1) {
        goto cleanup1;
    }
    //启动监听，准备接受客户端的连接请求
    if (listen(socketSrv, 15) == -1) {
        goto cleanup1;
    }


    while (true) {
        //等待客户端请求的到来，如果有客户端连接，则接受连接
        SOCKET socketClient = accept(socketSrv, (SOCKADDR *) &addrClient, &len);
        if (socketClient == -1) break;
        //向客户端发送“HelloWorld”消息
        send(socketClient, msg, strlen(msg), 0);
        closesocket(socketClient);
    }
cleanup1:
    closesocket(socketSrv);
cleanup2:
    WSACleanup();
    return 0;
}