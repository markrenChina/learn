//
// Created by mark on 2021/10/6.
//
#include <iostream>

using namespace std;
struct ListNode {
    int val;
    ListNode *next;

    ListNode() : val(0), next(nullptr) {}

    ListNode(int x) : val(x), next(nullptr) {}

    ListNode(int x, ListNode *next) : val(x), next(next) {}
};
    ListNode *_addTwoNumbers(ListNode *l1, ListNode *l2, bool add) {
        if (l1 == nullptr && l2 == nullptr) {
            if(add){
                auto* node = new ListNode(1);
                return node;
            } else {
                return nullptr;
            }
        }
        int val;
        if (l1 == nullptr) {
            if(add){
                val = ++(l2->val);
                if(val < 10){
                    return l2;
                }else {
                    l2->val = (val%10);
                    l2->next = _addTwoNumbers(nullptr,l2->next,true);
                    return l2;
                }
            } else {
                return l2;
            }
        } else if (l2 == nullptr) {
            if(add){
                val = ++(l1->val);
                if(val < 10){
                    return l1;
                } else{
                    l1->val = (val%10);
                    l1->next = _addTwoNumbers(l1->next, nullptr,true);
                    return l1;
                }
            } else {
                return l1;
            }
        } else {
            val = l1->val + l2->val;
            if(add) {
                val++;
            }
            l1->val = (val%10);
            l1->next = _addTwoNumbers(l1->next,l2->next,val/10);
        }
        return l1;
    }


    ListNode *addTwoNumbers(ListNode *l1, ListNode *l2) {
        l1 = _addTwoNumbers(l1,l2, false);
        return l1;
    }

    int main() {
        ListNode* l12 = new ListNode(9);
        ListNode* l11 = new ListNode(9,l12);
        ListNode* l10 = new ListNode(9,l11);

        ListNode* l24 = new ListNode(9);
        ListNode* l23 = new ListNode(9,l24);
        ListNode* l22 = new ListNode(9,l23);
        ListNode* l21 = new ListNode(9,l22);
        ListNode* l20 = new ListNode(9,l21);

        ListNode* res = addTwoNumbers(l10,l20);
        while (res!= nullptr){
            cout << res->val << endl;
            res = res->next;
        }
        //cout << 10%10 <<endl;
    }