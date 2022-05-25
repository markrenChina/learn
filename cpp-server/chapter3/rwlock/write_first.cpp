#include <pthread.h>
#include <unistd.h>
#include <iostream>

int resourceID = 0;
pthread_rwlock_t myrwlock;
using std::cout;

void* read_thread(void* param){
    
    while (true){
        //请求读锁
        pthread_rwlock_rdlock(&myrwlock);
        cout << "read thread ID: "<< pthread_self() << ",resourceID: " << resourceID << std::endl;
        //使用睡眠模拟读线程读的过程花了很长时间
        sleep(1);
        pthread_rwlock_unlock(&myrwlock);
    }
    return nullptr;
}

void* write_thread(void* param){
    while (true){
        //请求写锁
        pthread_rwlock_wrlock(&myrwlock);

        ++resourceID;
        cout << "write thread ID: " << pthread_self() <<", resourceID: " << resourceID << std::endl;
        //模拟时间消耗
        sleep(1);
        pthread_rwlock_unlock(&myrwlock);
    }
    return nullptr;
}

//g++ -g -o write_first write_first.cpp  -lpthread
//
int main(int argc, const char** argv) {
    //设置读写锁属性
    pthread_rwlockattr_t attr;
    pthread_rwlockattr_init(&attr);
    //设置成写者优先
    pthread_rwlockattr_setkind_np(&attr,PTHREAD_RWLOCK_PREFER_WRITER_NONRECURSIVE_NP);
    pthread_rwlock_init(&myrwlock, &attr);
    //pthread_rwlock_init(&myrwlock, nullptr);
    //创建5个请求读锁线程
    pthread_t readThreadID[5];
    for (int i = 0; i < 5; i++){
        pthread_create(&readThreadID[i], nullptr, read_thread, nullptr);
    }

    //创建1个请求写的线程
    pthread_t writeThreadID;
    pthread_create(&writeThreadID, nullptr, write_thread, nullptr);

    pthread_join(writeThreadID, nullptr);

    for (int i = 0; i < 5; i++){
        pthread_join(readThreadID[i], nullptr);
    }

    pthread_rwlock_destroy(&myrwlock);
    return 0;
}