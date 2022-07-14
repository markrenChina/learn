//
// Created by Ushop on 2022/7/14.
//
#include "../leetcode-utils.hpp"

class CQueue {
public:
    CQueue() {

    }

    void appendTail(int value) {
        add_s.push(value);
    }

    int deleteHead() {
        if(remove_s.empty()){
            while(!add_s.empty()){
                int tmp = add_s.top();
                remove_s.push(tmp);
                add_s.pop();
            }
        }
        if(remove_s.empty()){
            return -1;
        }
        int tmp = remove_s.top();
        remove_s.pop();
        return tmp;
    }

private:
    stack<int> add_s;
    stack<int> remove_s;
};

int main(){
    CQueue cQueue;
    cQueue.appendTail(3);
    cout << cQueue.deleteHead();
    cout << cQueue.deleteHead();

}