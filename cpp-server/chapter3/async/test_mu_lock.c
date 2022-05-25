#include <pthread.h>
#include <stdio.h>
#include <errno.h>
#include <unistd.h>

//gcc -g -o test test_mu_lock.c -lpthread
/**
ubuntu@ubuntu:~/cpp-server/chapter3/async$ gdb test
For help, type "help".
--Type <RET> for more, q to quit, c to continue without paging--c
Type "apropos word" to search for commands related to "word"...
Reading symbols from test...
(gdb) r
Starting program: /home/ubuntu/cpp-server/chapter3/async/test 
[Thread debugging using libthread_db enabled]
Using host libthread_db library "/lib/aarch64-linux-gnu/libthread_db.so.1".
ret = 0
^C
Program received signal SIGINT, Interrupt.
0x0000fffff7f9ab8c in __lll_lock_wait () from /lib/aarch64-linux-gnu/libpthread.so.0
(gdb) bt
#0  0x0000fffff7f9ab8c in __lll_lock_wait () from /lib/aarch64-linux-gnu/libpthread.so.0
#1  0x0000fffff7f92ba8 in pthread_mutex_lock () from /lib/aarch64-linux-gnu/libpthread.so.0
#2  0x0000aaaaaaaaaadc in main () at test_mu_lock.c:19
(gdb) q
 */
int main() {
    pthread_mutex_t mymutex;
    //声明一个mutex属性
    pthread_mutexattr_t mutex_attr;
    pthread_mutexattr_init(&mutex_attr);
    //书上为PTHREAD_MUTEX_NORMAL
    pthread_mutexattr_settype(&mutex_attr, PTHREAD_MUTEX_TIMED_NP);
    //mutex_attr 作为参数传递给mymutex
    pthread_mutex_init(&mymutex, &mutex_attr);

    int ret = pthread_mutex_lock(&mymutex);
    printf("ret = %d\n",ret);
    ret = pthread_mutex_lock(&mymutex);
    printf("ret = %d\n", ret);

    pthread_mutex_destroy(&mymutex);
    pthread_mutexattr_destroy(&mutex_attr);

    return 0;
}