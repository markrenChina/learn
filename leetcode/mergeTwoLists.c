//
// Created by mark on 2021/9/25.
//


#include <printf.h>

struct ListNode {
    int val;
    struct ListNode *next;
};

void _mergeTwoLists(struct ListNode *l1, struct ListNode *l2, struct ListNode *res) {

    if (NULL != l1 && NULL != l2) {
        if (l1->val < l2->val) {
            res->next = l1;
            _mergeTwoLists(l1->next, l2, res->next);
        }/* else if (l1 ->val == l2 ->val){
            res ->next = l1;
            _mergeTwoLists(l1->next, l2->next, res->next);
        }*/else {
            res->next = l2;
            _mergeTwoLists(l1, l2->next, res->next);
        }
    } else if (NULL == l1 && NULL != l2) {
        res->next = l2;
        _mergeTwoLists(l1, l2->next, res->next);
    } else if (NULL != l1) {
        res->next = l1;
        _mergeTwoLists(l1->next, l2, res->next);
    }
}

struct ListNode *mergeTwoLists(struct ListNode *l1, struct ListNode *l2) {
    struct ListNode res = {0, NULL};
    _mergeTwoLists(l1, l2, &res);
    l1 = res.next;
    return l1;
}


int main() {
    struct ListNode t41 = {4};
    struct ListNode t42 = {4};
    struct ListNode t3 = {3,&t41};
    struct ListNode t2 = {2,&t42};
    struct ListNode l1 = {1,&t2};
    struct ListNode l2 = {1,&t3};

    struct ListNode res1;
    //res1 = *mergeTwoLists(&l1, &l2);
    res1 = *mergeTwoLists(0, &t42);
    while (1){
        printf("%d ",res1.val);
        if(!res1.next){
            break;
        }
        res1 = *res1.next;
    }
    return 0;
}