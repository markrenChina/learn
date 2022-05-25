#ifndef UDP_LIBRARY_H
#define UDP_LIBRARY_H

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>


int UDP_Open(int port);

int UDP_FillSockAddr(struct sockaddr_in *addr, char *hostName, int port);

int UDP_Write(int sd, struct sockaddr_in *addr, char *buffer, int n);

int UDP_Read(int sd,struct sockaddr_in *addr,char *buffer, int n);

#endif // DEBUG