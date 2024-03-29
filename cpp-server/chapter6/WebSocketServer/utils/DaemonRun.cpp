/**
* ???????????????????
* zhangyl 2018.08.20
*/
#ifndef _WIN32
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <iostream>
#include "DaemonRun.h"

void daemon_run()
{
    int pid;
    signal(SIGCHLD, SIG_IGN);
    //1??????????��?fork??????????????????ID??
    //2??????????��?fork????0??
    //3????????????fork????????????
    pid = fork();
    if (pid < 0)
    {
        std::cout << "fork error" << std::endl;
        exit(-1);
    }
    //???????????????????????
    else if (pid > 0) {
        exit(0);
    }
    //??parent??child??????????session??,parent?????session???????????,
    //parent??????????????????????exit??????��???????????????????????????init??????
    //???setsid()???,child???????????????(session)id??
    //???parent??????,?????????child???
    setsid();
    int fd;
    fd = open("/dev/null", O_RDWR, 0);
    if (fd != -1)
    {
        dup2(fd, STDIN_FILENO);
        dup2(fd, STDOUT_FILENO);
        dup2(fd, STDERR_FILENO);
    }
    if (fd > 2)
        close(fd);
}
#endif