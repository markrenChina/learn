#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "udp_library.h"

const int BUFFER_SIZE = 2000;

int main(int argc, char const *argv[])
{
    int sd = UDP_Open(10000);
    assert(sd > -1);
    while(1) {
        struct sockaddr_in s;
        char buffer[BUFFER_SIZE];
        int rc = UDP_Read(sd, &s, buffer, BUFFER_SIZE);
        printf("%s",buffer);
        if (rc > 0){
            char reply[BUFFER_SIZE];
            sprintf(reply, "server : reply\n");
            rc = UDP_Write(sd, &s, reply, BUFFER_SIZE);
        }
    }
    return 0;
}
