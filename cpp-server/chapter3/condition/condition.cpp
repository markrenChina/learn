//
// Created by Ushop on 2022/5/25.
//
#include <Windows.h>
#include <iostream>
#include <list>

#ifdef UNICODE
#define tcout std::wcout //unicode enabled
#else
#define tcout std::cout
#endif

class Task {
public:
    Task(int taskId) : taskId(taskId) {}
    void doTask(){
        tcout << TEXT("handle a task, taskID: ")<< taskId << TEXT(", threadID: ") << GetCurrentThreadId() << std::endl;
    }
private:
    int taskId;
};

CRITICAL_SECTION myCriticalSection;
CONDITION_VARIABLE myConditionVar;
std::list<Task*> tasks;

DWORD WINAPI consumerThread(LPVOID param) {
    Task* pTask = NULL;
    while (true){
        //进入临界区
        EnterCriticalSection(&myCriticalSection);
        //消费队列为空时挂起释放锁
        while (tasks.empty()){
            //如果SleepConditionVariableCS挂起，则挂起前会离开临界区，不往下执行
            //发生变化后，条件合适时，SleepConditionVariableCS将直接进入临界区
            SleepConditionVariableCS(&myConditionVar,&myCriticalSection,INFINITE);
        }
        pTask = tasks.front();
        tasks.pop_front();

        //为了让其他线程有机会操作tasks，这里需要再次离开临界区
        LeaveCriticalSection(&myCriticalSection);

        if (pTask == nullptr){
            continue;
        }

        pTask->doTask();
        delete pTask;
        pTask = nullptr;
    }
    return 0;
}

DWORD WINAPI producerThread(LPVOID param){
    int taskID = 0;
    Task* pTask = nullptr;

    while (taskID < 100){
        pTask = new Task(taskID);

        //进入临界区
        EnterCriticalSection(&myCriticalSection);
        tasks.push_back(pTask);
        tcout << TEXT("produce a task,taskID: ") << taskID << TEXT(", threadID: ") << GetCurrentThreadId() << std::endl;

        //离开临界区
        LeaveCriticalSection(&myCriticalSection);
        //唤醒条件变量
        WakeConditionVariable(&myConditionVar);

        taskID++;

        //休眠1秒
        Sleep(1);
    }
    return 0;
}


int main(){
    InitializeConditionVariable(&myConditionVar);
    InitializeCriticalSection(&myCriticalSection);

    //创建5个消费者线程
    HANDLE consumerThreadHandles[5];
    for (auto & i : consumerThreadHandles) {
        i = CreateThread(nullptr,0,consumerThread, nullptr,0, nullptr);
    }
    //创建一个生产者线程
    HANDLE producerThreadHandle =  CreateThread(nullptr,0,producerThread, nullptr,0, nullptr);
    //等待一个生产者线程退出
    WaitForSingleObject(producerThreadHandle,INFINITE);
    //等待消费者线程退出
    for (auto &i:consumerThreadHandles) {
        WaitForSingleObject(i,INFINITE);
        CloseHandle(i);
    }

    DeleteCriticalSection(&myCriticalSection);
    return 0;
}