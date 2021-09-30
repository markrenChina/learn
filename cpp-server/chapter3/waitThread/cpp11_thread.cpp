//
// Created by Ushop on 2021/9/30.
//
#include <cstdio>
#include <ctime>
#include <thread>
#include <fstream>
#include <iostream>

#define TIME_FILENAME "time.txt"
/**
 * 替换书上的代码，采用cpp风格
 */
void FileThreadFunc(){
    using namespace std;
    time_t now = time(nullptr);
    struct tm* t = localtime(&now);
    char timeStr[32] = {0};
    snprintf(timeStr, 32, "%04d/%02d/%02d %02d:%02d:%02d\n",
             t -> tm_year + 1900,
             t -> tm_mon + 1,
             t -> tm_mday,
             t -> tm_hour,
             t -> tm_min,
             t -> tm_sec);
    ofstream outFS;
    outFS.open(TIME_FILENAME);
    if (!outFS.is_open()){
        cout << "Failed to create time.txt" << endl;
        return;
    }
    outFS << timeStr <<endl;
    outFS.close();
}

int main() {
    using namespace std;
    thread t(FileThreadFunc);
    if (t.joinable()){
        t.join();
    }
    ifstream inFO;
    inFO.open(TIME_FILENAME);
    if (!inFO.is_open()){
        cout << "open file error.\n";
        return -2;
    }
    char buf[32] = { 0 };
    inFO >> buf;
    cout<<"Current Time is: " << buf;
    inFO.close();
}