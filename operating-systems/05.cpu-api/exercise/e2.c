#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <assert.h>
#include <sys/wait.h>

int
main(int argc, char *argv[])
{
    close(STDOUT_FILENO);
    open("./e2.output", O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU);
    int rc = fork();
    if (rc < 0) {
        fprintf(stderr, "fork failed\n");
        exit(1);
    } else if (rc == 0) {
        for (int i = 0; i < 20; ++i)
        {
            printf("child\n");
        } 
        wait(NULL);
        for (int i = 0; i < 20; ++i)
        {
            printf("child\n");
        }
    } else {
        wait(NULL);
        for (int i = 0; i < 20; ++i)
        {
            printf("parent\n");
        }
    }
    return 0;
}