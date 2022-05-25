#include "udp_library.h"
#include <string.h>
#include <sys/types.h>
#include <netdb.h>
#include <unistd.h>

int UDP_Open(int port){
    int sd;
    if ((sd = socket(AF_INET, SOCK_DGRAM, 0)) == -1){return -1;}
    struct sockaddr_in myaddr;
    //bzero 把字符串前几位置0
    bzero(&myaddr,sizeof(myaddr));
    myaddr.sin_family       = AF_INET;
    myaddr.sin_port         = htons(port);
    myaddr.sin_addr.s_addr  = INADDR_ANY;

    if (bind(sd, (struct sockaddr *) &myaddr, sizeof(myaddr)) == -1){
        close(sd);
        return -1;
    }
    return sd;
}

int UDP_FillSockAddr(struct sockaddr_in *addr, char *hostName, int port){
    bzero(addr, sizeof(struct sockaddr_in));
    addr-> sin_family = AF_INET;
    addr-> sin_port = htons(port);
    struct in_addr *inAddr;
    struct hostent *hostEntry;
    if ((hostEntry = gethostbyname(hostName))==NULL){ return -1; }
    inAddr = (struct in_addr *) hostEntry->h_addr;
    addr->sin_addr = *inAddr;
    return 0;
}

int UDP_Write(int sd, struct sockaddr_in *addr, char *buffer, int n){
    int addrLen = sizeof(struct sockaddr_in);
    return sendto(sd,buffer, n, 0,(struct sockaddr *)addr, addrLen);
}

int UDP_Read(int sd,struct sockaddr_in *addr,char *buffer, int n){
    int len = sizeof(struct sockaddr_in);
    int rc = recvfrom(sd, buffer, n, 0,(struct sockaddr *)addr,(socklen_t *) &len);
    return rc;
}