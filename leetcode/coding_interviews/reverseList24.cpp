//
// Created by Ushop on 2022/7/15.
//
#include "../leetcode-utils.hpp"

struct ListNode {
    int val;
    ListNode *next;

    ListNode(int x) : val(x), next(NULL) {}
};
/**
 * 最佳答案
 * ListNode* reverseList(ListNode* head) {
        ListNode* pre =NULL;
        while(head){
            ListNode* temp = head->next;
            head->next = pre;
            pre = head;
            head = temp;
        }
        return pre;
    }
 */
class Solution {
public:
    ListNode *reverseList(ListNode *head) {
        if (!head) {
            return head;
        }
        ListNode *tmp;
        ListNode *reverse;
        if (head->next) {
            reverse = head;
            head = head->next;
            reverse->next = nullptr;
        } else {
            return head;
        }
        while (head->next) {
            tmp = reverse;
            reverse = head;
            head = head->next;
            reverse->next = tmp;
        }
        tmp = reverse;
        reverse = head;
        reverse->next = tmp;
        return reverse;
    }
};
