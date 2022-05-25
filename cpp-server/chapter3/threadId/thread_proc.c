#include <sys/syscall.h>
#include <unistd.h>
#include <stdio.h>
#include <pthread.h>

//bash$ gcc thread_proc.c -o thread -lpthread
void* thread_proc(void* arg){
    //获取线程ID
    pthread_t* tid1 = (pthread_t* )arg;
    //获取LWP 轻量级进程ID
    int tid2 = syscall(SYS_gettid);
    //获取线程ID
    pthread_t tid3 = pthread_self();

    while(1){
        printf("tid1: %ld, tid2 %d, tid3 %ld\n",*tid1,tid2,tid3);
        sleep(10000);
    }
}

int main(){
    pthread_t tid;
    pthread_create(&tid, NULL, thread_proc, &tid);
    pthread_join(tid, NULL);

    return 0;
}