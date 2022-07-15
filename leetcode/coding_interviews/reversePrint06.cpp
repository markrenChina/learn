//
// Created by Ushop on 2022/7/15.
//
#include "../leetcode-utils.hpp"

struct ListNode {
    int val;
    ListNode *next;

    ListNode(int x) : val(x), next(NULL) {}
};

//高分答案:计算长度，倒着往vector里面装
//剑指解题思路，后进先出用栈倒一下，可以不破坏原数据结构。
//栈可以改为递归。
class Solution {
public:
    vector<int> reversePrint(ListNode* head) {
        //1.反转单链表
        int size = 1;
        ListNode* resvse;
        ListNode* tmp;
        if (!head){
            return {};
        }
        if(head->next) {
            resvse = head;
            head = head->next;
            resvse->next= nullptr;
            size++;
        }else {
            return {head->val};
        }
        while(head->next){
            tmp = resvse;  //1
            resvse = head; //3
            head = head->next; //2
            size++;
            resvse->next = tmp;  //3 -> 1
        }
        tmp = resvse; //3
        resvse = head; //2
        resvse->next = tmp; //2 -> 3
        //2.输出结果，优化可以在单链反转时，直接写入，但是可能会遇到vector扩容消耗
        vector<int> vec;
        vec.resize(size);
        for (int i = 0; i < size; ++i) {
            vec[i] = (resvse->val);
            resvse = resvse->next;
        }
        return vec;
    }
};

int main(){
    Solution solution;
    ListNode two(2);
    ListNode tir(3);
    ListNode fir(1);
    fir.next = &tir;
    tir.next = &two;
    vector<int> bk = solution.reversePrint(&fir);
    for_each(bk.begin(), bk.end(),[](auto &e){
        cout << e <<" ";
    });
    return 0;
}