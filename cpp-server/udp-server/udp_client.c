#include <stdio.h>
#include <stdlib.h>
#include "udp_library.h"

const int BUFFER_SIZE = 2000;

int main(int argc, char const *argv[])
{
    int sd = UDP_Open(20000);
    struct sockaddr_in addr,addr2;
    //配置请求地址
    //int rc = UDP_FillSockAddr(&addr,"machine.cs.wisc.edu",10000);
    int rc = UDP_FillSockAddr(&addr,"pi.ccand99.com",10000);
    char message[BUFFER_SIZE];
    sprintf(message, "client : hello world\n");
    char buffer[BUFFER_SIZE];
    rc = UDP_Write(sd, &addr, message, BUFFER_SIZE);
    if (rc > 0)
    {
        //addr2等于监听20000udp窗口
        int ret = UDP_Read(sd, &addr2,buffer ,BUFFER_SIZE);
        if (ret > 0){
            printf("%s",buffer);
        }
    }
    return 0;
}
