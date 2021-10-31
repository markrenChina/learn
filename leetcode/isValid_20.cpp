//
// Created by mark on 2021/10/8.
//

#include <iostream>
#include <stack>

using namespace std;

    bool isValid(string s) {
        stack<char > stack;
        for (auto e: s) {
            switch (e) {
                case '(':
                case '{':
                case '[': stack.push(e);break;
                case ')': if (!stack.empty() && stack.top() == '(') {
                        stack.pop();
                    } else {
                        return false;
                    }
                    break;
                case '}': if (!stack.empty() && stack.top() == '{') {
                        stack.pop();
                    } else {
                        return false;
                    }
                    break;
                case ']': if (!stack.empty() && stack.top() == '[') {
                        stack.pop();
                    } else {
                        return false;
                    }
                    break;
                default:break;
            }
        }
        if (stack.empty()){
            return true;
        }
        return false;
    }

    int main() {
        //cout << isValid("()[]{}") << endl;
        cout << isValid("}") << endl;
    }