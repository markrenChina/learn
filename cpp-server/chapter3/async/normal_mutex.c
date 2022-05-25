#include <pthread.h>
#include <stdio.h>
#include <errno.h>
#include <unistd.h>

//gcc -o normal_mutex normal_mutex.c -lpthread
pthread_mutex_t mymutex;
int             resourceNo = 0;

void* worker_thread(void* param){
    //获取线程id
    pthread_t threadID = pthread_self();
    printf("thread start, ThreadID: %ld\n", threadID);

    while (1){
        //锁定 mymutex
        pthread_mutex_lock(&mymutex);
        printf("Mutex lock, resourceNo: %d, ThreadID: %ld\n",resourceNo, threadID);
        resourceNo++;

        printf("Mutex unlock, resourceNo: %d, ThreadId: %ld\n",resourceNo,threadID);

        pthread_mutex_unlock(&mymutex);
        //休眠1秒
        sleep(10);
    }
    return NULL;
}

int main(){
    //声明一个mutex属性
    pthread_mutexattr_t mutex_attr;
    pthread_mutexattr_init(&mutex_attr);
    //书上为PTHREAD_MUTEX_NORMAL
    pthread_mutexattr_settype(&mutex_attr, PTHREAD_MUTEX_TIMED_NP);
    //mutex_attr 作为参数传递给mymutex
    pthread_mutex_init(&mymutex, &mutex_attr);

    //创建5个工作线程
    pthread_t threadID[5];
    for (int i = 0; i < 5; i++){
        pthread_create(&threadID[i], NULL,worker_thread,NULL);
    }
    for (int i = 0; i < 5; i++){
        pthread_join(threadID[i],NULL);
    }
    pthread_mutex_destroy(&mymutex);
    pthread_mutexattr_destroy(&mutex_attr);

    return 0;
}