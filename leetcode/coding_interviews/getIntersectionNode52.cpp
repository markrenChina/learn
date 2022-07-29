//
// Created by Ushop on 2022/7/25.
#include "../leetcode-utils.hpp"

struct ListNode {
    int val;
    ListNode *next;

    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode *getIntersectionNode(ListNode *headA, ListNode *headB) {
        set<ListNode *> list;
        ListNode *tmpA = headA;
        while (tmpA) {
            list.insert(tmpA);
            tmpA = tmpA->next;
        }
        ListNode *tmpB = headB;
        while (tmpB) {
            auto it = list.insert(tmpB);
            if (it.second) {
                tmpB = tmpB->next;
            } else {
                break;
            }
        }
        return tmpB;
    }
};

int main() {
    ListNode *a_1 = new ListNode(4);
    ListNode *a_2 = new ListNode(1);
    ListNode *b_1 = new ListNode(5);
    ListNode *b_2 = new ListNode(0);
    ListNode *b_3 = new ListNode(1);
    ListNode *c_1 = new ListNode(8);
    ListNode *c_2 = new ListNode(4);
    ListNode *c_3 = new ListNode(5);
    c_1->next = c_2;
    c_2->next = c_3;
    a_1->next = a_2;
    a_2->next = c_1;
    b_1->next = b_2;
    b_2->next = b_3;
    b_3->next = c_1;
    Solution solution;
    cout << solution.getIntersectionNode(a_1,b_1)->val;
}