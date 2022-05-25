#include <iostream>
#include <csignal>
#include <unistd.h>

void prog_exit(int signo){
    std::cout << "Program recv signal [" << signo << "] to exit." << std::endl;
}

int main(int argc, char* argv[]) {
    std::cout << "start main" << std::endl;
    //设置信号处理
    signal(SIGCHLD, SIG_DFL);
    signal(SIGPIPE, SIG_IGN);
    signal(SIGINT , prog_exit);
    signal(SIGTERM, prog_exit);

    int ch;
    bool bdaemon = false;
    while ((ch = getopt(argc, argv, "d")) != -1){
        switch (ch){
        case 'd':
            bdaemon = true;
            break;
        }
    }
    if (bdaemon){
        std::cout << "run" << std::endl;
    }
    std::cout << "finnish" << std::endl;
    return 0;
}