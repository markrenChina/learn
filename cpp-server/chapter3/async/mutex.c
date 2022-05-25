#include <pthread.h>

//PTHREAD_MUTEX_INITIALIZERLIZER  是一个结构常量
pthread_mutex_t mymutext = PTHREAD_MUTEX_INITIALIZER;

int main(int argc, char const *argv[])
{
    pthread_mutex_t mymutex;
    pthread_mutex_init(&mymutex, NULL);
    
    return 0;
}
