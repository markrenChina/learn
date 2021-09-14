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
    int rc = fork();
    if (rc < 0) {
        fprintf(stderr, "fork failed\n");
        exit(1);
    } else if (rc == 0) {
        char *myargs[3];
        myargs[0] = strdup("ls");
        myargs[1] = strdup("-l"); 
        myargs[2] = NULL;
        //char args[3];
        //pass        
        //execvp(myargs[0], myargs);
        //pass
        //execlp(myargs[0], myargs[0],myargs[1],myargs[2]);
        //pass
        //execl("/bin/ls", myargs[0],myargs[1],myargs[2]);
        //pass
        execle("/bin/ls", myargs[0],myargs[1],myargs[2]);
    } else {
        int wc = wait(NULL);
	    assert(wc >= 0);
    }
    return 0;
}