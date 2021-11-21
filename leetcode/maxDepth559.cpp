//
// Created by mark on 2021/11/21.
//

#include <vector>
#include <iostream>
#include <queue>

using namespace std;
// Definition for a Node.
class Node {
public:
    int val;
    vector<Node*> children;

    Node() {}

    Node(int _val) {
        val = _val;
    }

    Node(int _val, vector<Node*> _children) {
        val = _val;
        children = _children;
    }
};




class Solution {
public:

    int maxDepth(Node* root) {
        queue<Node*> queue;
        int res = 0;
        if (nullptr == root){
            return res;
        }
        Node* temp = root;
        queue.push(temp);
        while (!queue.empty()){
            int size = (int)queue.size();
            for (int j = 0; j < size; ++j) {
                temp = (Node*)queue.front();
                for (auto& e : temp->children){
                    queue.push(e);
                }
                queue.pop();
            }
            res++;
        }
        return res;
    }
};

int main() {

    Solution solution;
    Node* root =new  Node(1);
    int dep = solution.maxDepth(root);
    delete root;
    cout << dep << endl;
}

