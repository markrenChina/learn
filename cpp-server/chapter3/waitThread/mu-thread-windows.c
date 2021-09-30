//
// Created by Ushop on 2021/9/30.
//
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <Windows.h>

#define TIME_FILENAME "time.txt"

//windows 下函数返回方式WINAPI代表是stdcall
DWORD WINAPI FileThreadFunc(LPVOIDlpParameters){
    time_t now = time(NULL);
    struct tm *t = localtime(&now);
    char timeStr[32] = {0};
    sprintf_s(timeStr, 32, "%04d/%02d/%02d %02d:%02d:%02d\n",
        t -> tm_year + 1900,
        t -> tm_mon + 1,
        t -> tm_mday,
        t -> tm_hour,
        t -> tm_min,
        t -> tm_sec);
        //文件不存在则创建；存在则覆盖
    FILE* fp = fopen(TIME_FILENAME,"w");
    if (fp == NULL){
        printf("Failed to create time.txt.\n");
        return 1;
    }
        size_t sizeToWrite = strlen(timeStr) + 1;
        size_t ret = fwrite(timeStr, 1, sizeToWrite, fp);
        if (ret != sizeToWrite)
        {
            printf("Write file error. \n");
        }
        fclose(fp);
        return 2;
}

int main(){
    DWORD dwFileThreadID;
    HANDLE hFileThread = CreateThread(NULL, 0, FileThreadFunc, NULL, 0, &dwFileThreadID);

    if (hFileThread == NULL) {
        printf("Failed to create fileThread.\n");
        return -1;
    }

    //无限等待，直到文件线程退出，否则程序将一直挂起
    WaitForSingleObject(hFileThread, INFINITE);

    //使用r选项，要求文件必须存在
    FILE * fp = fopen(TIME_FILENAME,"r");
    if (fp == NULL){
        printf("open file error.\n");
        return -1;
    }

    char buf[32] = { 0 };
    int sizeRead = fread(buf, 1, 32, fp);
    if (sizeRead == 0){
        printf("read file error.\n");
        fclose(fp);
        return -3;
    }
    printf("Current Time is: %s.\n", buf);

    fclose(fp);
    return 0;
}