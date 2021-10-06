//
// Created by mark on 2021/9/29.
//

#include <stdio.h>
#include <string.h>

int strStr(char * haystack, char * needle){
    if (*needle == '\0'){
        return 0;
    }
    int sizeH = strlen(haystack);
    int sizeN= strlen(needle);
    if(sizeH < sizeN) {
        return -1;
    }
    int offset = -1;
    do {
        offset++;
        if(*(haystack + offset) == *(needle)){
            int diff = 1;
            while(*(needle + diff) != '\0'){
                if(*(needle +diff) != *(haystack + offset + diff)){

                    goto fail;
                }
                diff++;
            }
            return offset;
        }
        fail: {};
    }while((sizeN + offset) < sizeH);
    return -1;
}

int main(int argc, char const *argv[])
{
    int res = strStr("","a");
    printf("%d\n",res);
    int res1 = strStr("hello","ll");
    printf("%d",res1);
    return 0;
}