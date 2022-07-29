
#include "../leetcode-utils.hpp"

struct ListNode {
    int val;
    ListNode *next;

    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    ListNode *deleteNode(ListNode *head, int val) {
        ListNode *tmp = head;
        ListNode *pre = nullptr;
        while (tmp) {
            if (tmp->val == val) {
                if (pre) {
                    pre->next = tmp->next;
                } else {
                    head = tmp->next;
                }
                delete tmp;
                break;
            }
            pre = tmp;
            tmp = tmp->next;
        }
        return head;
    }
};

int main() {
    Solution solution;
    ListNode* f = new ListNode(4);
    ListNode* s = new ListNode(5);
    f->next = s;
    cout << solution.deleteNode(f,4)->val;

}