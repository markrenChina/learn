#include "../leetcode-utils.hpp"

//
// Created by Ushop on 2022/7/14.
//
class MinStack {
public:
    /** initialize your data structure here. */
    MinStack() {
        help_s.push(INT32_MAX);
    }

    void push(int x) {
        if (help_s.top() >= x ){
            help_s.push(x);
        }
        stack.push(x);
    }

    void pop() {
        if (stack.top() == help_s.top()){
            help_s.pop();
        }
        stack.pop();
    }

    int top() {
        return stack.top();
    }

    int min() {
        return help_s.top();
    }
private:
    stack<int> help_s;
    stack<int> stack;
};

int main(){
    MinStack minStack;
    minStack.push(-2);
    minStack.push(0);
    minStack.push(-3);
    cout << minStack.min() << endl;
    minStack.pop();
    cout << minStack.top() << endl;
    cout << minStack.min() << endl;
}