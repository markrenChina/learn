//
// Created by Ushop on 2022/5/25.
//
#include <Windows.h>
#include <iostream>
#include <list>
#include <mutex>

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

std::mutex myMutex;
std::condition_variable mycv;
std::list<Task*> tasks;

DWORD WINAPI consumerThread(LPVOID param) {
    Task* pTask = NULL;
    while (true){
        {
            std::unique_lock<std::mutex> guard(myMutex);
            while (tasks.empty()){
                mycv.wait(guard);
            }
            pTask = tasks.front();
            tasks.pop_front();
        }

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
        //使用作用域减少锁的范围
        {
            std::lock_guard<std::mutex> guard(myMutex);
            tasks.push_back(pTask);
            tcout << TEXT("produce a task,taskID: ") << taskID << TEXT(", threadID: ") << GetCurrentThreadId() << std::endl;
        }
        //释放信号量，通知消费者线程
        mycv.notify_one();
        taskID++;
        //休眠1秒
        Sleep(1);
    }
    return 0;
}


int main(){

    //创建5个消费者线程
    HANDLE consumerThreadHandles[5];
    for (auto & i : consumerThreadHandles) {
        i = CreateThread(nullptr,0,consumerThread, nullptr,0, nullptr);
    }
    //创建一个生产者线程
    HANDLE producerThreadHandle =  CreateThread(nullptr,0,producerThread, nullptr,0, nullptr);
    //等待一个生产者线程退出
    WaitForSingleObject(producerThreadHandle,INFINITE);
    CloseHandle(producerThreadHandle);
    //等待消费者线程退出
    for (auto &i:consumerThreadHandles) {
        WaitForSingleObject(i,INFINITE);
        CloseHandle(i);
    }
    tcout << TEXT("end") << std::endl;
    return 0;
}