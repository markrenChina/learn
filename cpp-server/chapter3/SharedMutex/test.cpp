//
// Created by mark on 2022/5/24.
//
#include "SharedMutex.h"
#include <iostream>
#include <thread>
#include <vector>

size_t count = 0;
SharedMutex mutex;

#ifdef WIN32
DWORD WINAPI fun1(LPVOID pVoid) {
    for (size_t i = 0; i < 10000; ++i) {
        //SharedLockGuard sharedLockGuard(mutex); //读锁 ，锁不住
        UniqueLockGuard uniqueLockGuard(mutex); //写锁
        ++count;
        //std::this_thread::yield(); //主动让渡时间片增加出错概率
    }
    return 0;
}
#else
void * fun1(void *pVoid) {
    for (size_t i = 0; i < 10000; ++i) {
        SharedLockGuard sharedLockGuard(mutex); //读锁 ，锁不住
        //UniqueLockGuard uniqueLockGuard(mutex); //写锁
        ++count;
        std::this_thread::yield(); //主动让渡时间片增加出错概率
    }
}
#endif

int main(){
#ifdef WIN32
    std::vector<HANDLE> threads;
    for (int i = 0; i < 10; ++i) {
        DWORD tid;
        threads.push_back(CreateThread(nullptr,0,fun1, nullptr,0, &tid));
    }
    for (int i = 0; i < 10; ++i) {
        WaitForSingleObject(threads[i],INFINITE);
        CloseHandle(threads[i]);
    }
    std::wcout << TEXT("count=") << count;
#else
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
#endif


}