//
// Created by Ushop on 2022/7/15.
//
// Definition for a Node.

#include "../leetcode-utils.hpp"

class Node {
public:
    int val;
    Node* next;
    Node* random;

    Node(int _val) {
        val = _val;
        next = NULL;
        random = NULL;
    }
};
class Solution {
public:
    Node* copyRandomList(Node* head) {
        if(!head){
            return nullptr;
        }
        Node* tmp = head;
        //1.在原链表上每个节点复制自身，并指向自身的复制节点
        while(head){
            Node* newNode = new Node(head->val);
            newNode->next = head->next;
            head->next = newNode;
            head = newNode->next;
        }
        //2.复制节点的random节点就是前一个自身的random节点的next（random的copy）
        head = tmp;
        while(head){
            if (head->random){
                head->next->random = head->random->next;
            }
            head = head->next->next;

        }
        //3.拆分2个链表
        head = tmp;
        tmp = head->next;
        Node* newList = nullptr;
        while(head){
            newList = head->next;
            head->next = newList->next;
            head = head->next;
            if (head){
                newList->next = head->next;
            }
        }
        return tmp;
    }
};

int main(){
    Solution solution;
    Node *test1 = new Node(7);
    Node *test2 = new Node(13);
    Node *test3 = new Node(11);
    Node *test4 = new Node(10);
    Node *test5 = new Node(1);
    test1->next=test2;
    test2->next=test3;
    test3->next=test4;
    test4->next=test5;

    test2->random =test1;
    test3->random = test5;
    test4->random = test3;
    test5->random = test1;
    Node* bk = solution.copyRandomList(test1);
    for (; bk; bk = bk->next) {
        cout << bk->val << " " ;
    }
    return 0;
}