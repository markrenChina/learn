//
// Created by mark on 2022/5/24.
//
#include <iostream>
#include <thread>
#include <vector>

#include "SharedMutex.h"

size_t count = 0;
SharedMutex mutex;

void * fun1(void *pVoid) {
    for (size_t i = 0; i < 10000; ++i) {
        SharedLockGuard sharedLockGuard(mutex); //读锁 ，锁不住
        //UniqueLockGuard uniqueLockGuard(mutex); //写锁
        ++count;
        std::this_thread::yield(); //主动让渡时间片增加出错概率
    }
}
int main(){
    std::vector<pthread_t> threads;
    for (int i = 0; i < 10; ++i) {
        pthread_t thread;
        pthread_create(&thread, nullptr,fun1, nullptr);
        threads.push_back(thread);
    }

    for (int i = 0; i < 10; ++i) {
        //pthread_detach(threads[i]);
        pthread_join(threads[i], nullptr);
    }

    std::cout << "count=" << count;
}