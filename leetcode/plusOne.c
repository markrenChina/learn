//
// Created by mark on 2021/10/1.
//
#include <stdlib.h>
#include <stdio.h>

int *plusOne(int *digits, int digitsSize, int *returnSize) {
    for (int i = digitsSize-1; i > 0; --i) {
        if (digits[i] != 9){
            digits[i]++;
            *returnSize = digitsSize;
            return digits;
        }else {
            digits[i] = 0;
        }
    }
    if(digits[0] == 9){
        *returnSize = digitsSize+1;
        int *res = (int *)malloc(sizeof(int)*(*returnSize));
        res[0] = 1;
        for (int i= 1;i < *returnSize;i++){
            res[i]  = 0;
        }
        return res;
    } else {
        digits[0]++;
        *returnSize = digitsSize;
        return digits;
    }
}

int main(){
    int test[] = { 1,2,3 };
    int ret;
    int *res = plusOne(test,3,&ret);
    printf("%d\n",res[2]);
    int test2[] = { 9,9 };
    int ret2;
    int *res2 = plusOne(test2,2,&ret2);
    printf("%d\n",res2[2]);
}

