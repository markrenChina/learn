//
// Created by mark on 2021/10/16.
//

#include <iostream>
#include <vector>

using namespace std;

class Solution {
private:
    class Node{
    public:
        Node(char o, int tar_ad, string v): operator_char(o), target(tar_ad), val(v){}
        //返回val + operator_char的字符串
        char operator_char;
        int target;
        Node* operator_next_node = nullptr;

        string val;

        int getVal(){
            return atoi(val.c_str());
        }
    };

    vector<Node*> res;

    void addNode(string num,int startIndex,int endIndex,Node* next){
        int value = next->getVal();
        num[endIndex] = '\0';
        Node* node = new Node('+', (next->target - value), &num[startIndex]);
        node->operator_next_node = next;
        if (startIndex == 0){
            int preInt = atoi(num.c_str());
            if (preInt == node->target && next->operator_char != '*'){
                res.push_back(node);
                return;
            } else if (next->operator_char == '*'){
                Node * temp = next;
                long temp_val =1;
                while (temp->operator_char == '*'){
                    temp_val *= atoi(temp->val.c_str());
                    temp = temp->operator_next_node;
                }
                temp_val *= atoi(temp->val.c_str());
                if ((preInt + temp_val) == temp->target){
                    res.push_back(node);
                    return;
                }
            }

            delete node;
            return;

        } else {
            if (next->operator_char == '*'){
                Node * temp = next;
                long temp_val =1;
                while (temp->operator_char == '*'){
                    temp_val *= atoi(temp->val.c_str());
                    temp = temp->operator_next_node;
                }
                temp_val *= atoi(temp->val.c_str());
                node->target = temp->target - temp_val ;
            }
            _addOperators(num,startIndex,node);
        }
    }

    void minusNode(string num,int startIndex,int endIndex,Node* next){
        int value = atoi(next->val.c_str());
        num[endIndex] = '\0';
        Node* node = new Node('-', (next->target + value), &num[startIndex]);
        node->operator_next_node = next;
        if (startIndex == 0){
            int preInt = atoi(num.c_str());
            if (preInt == node->target && next->operator_char != '*'){
                res.push_back(node);
                return;
            } else if (next->operator_char == '*'){
                Node * temp = next;
                long temp_val =1;
                while (temp->operator_char == '*'){
                    temp_val *= atoi(temp->val.c_str());
                    temp = temp->operator_next_node;
                }
                temp_val *= atoi(temp->val.c_str());
                if ((preInt - temp_val) == temp->target){
                    res.push_back(node);
                    return;
                }
            }
            delete node;
            return;
        } else {
            if (next->operator_char == '*'){
                Node * temp = next;
                long temp_val =1;
                while (temp->operator_char == '*'){
                    temp_val *= atoi(temp->val.c_str());
                    temp = temp->operator_next_node;
                }
                temp_val *= atoi(temp->val.c_str());
                node->target = temp->target + temp_val ;
            }
            _addOperators(num,startIndex,node);
        }
    }

    void multipliedNode(string num,int startIndex,int endIndex,Node* next){
        if (next->val[0] == '0'&& next->val[1]!='\0'){
            return;
        }
        long value = atoi(next->val.c_str());
        num[endIndex] = '\0';
        int target;
        if (value == 0){
            target = next->target;
        } else{
            target = (next->target / value);
        }
        Node* node = new Node('*', target, &num[startIndex]);
        node->operator_next_node = next;
        if (startIndex == 0){
            int preInt = atoi(num.c_str());
            if (next->operator_char == '*'){
                Node * temp = next;
                long temp_val =1;
                while (temp->operator_char == '*'){
                    temp_val *= atoi(temp->val.c_str());
                    temp = temp->operator_next_node;
                }
                temp_val *= atoi(temp->val.c_str());
                if ((preInt * temp_val) == temp->target){
                    res.push_back(node);
                    return;
                }
            } else if ((preInt * value) == next->target){
                res.push_back(node);
                return;
            }
            delete node;
            return;
        } else {

            _addOperators(num,startIndex,node);
        }
    }
    //endIndex是不包含的位
    void _addOperators(string num,int endIndex,Node* next){
        for (int i = 0; i < endIndex; ++i) {
            addNode(num,i,endIndex,next);
            minusNode(num,i,endIndex,next);
            multipliedNode(num,i,endIndex,next);
        }
    }

public:
    vector<string> addOperators(string num, int target) {
        for (int i = (int)num.size() - 1; i >= 0; --i) {
            //&num[i+1] 只能在这里用，后面应该clone一份
            Node* tail = new Node('\0',target,num.substr(i));
            _addOperators(num,i,tail);
        }
        vector<string> ret = vector<string>();
        for (int i = 0; i < res.size(); ++i) {
            string str;
            Node* temp = res[i];
            Node* deleteNode;
            while (temp->operator_next_node != nullptr){
                if (temp->val.size() >1 && atoi(temp->val.c_str()) == 0){
                    goto label;
                }
                str.append(temp->val + temp->operator_char);
                deleteNode = temp;
                temp = temp->operator_next_node;
                //delete deleteNode;
            }
            if (temp->val.size() >1 && atoi(temp->val.c_str()) == 0){
                goto label;
            }
            str.append(temp->val);
            //delete temp;
            ret.push_back(str);
            label:
            continue;
        }
        return ret;
    }
};

int main(){
    Solution solution;
    vector<string> test;
    //test = solution.addOperators("123",6);
    //test = solution.addOperators("105",5);
    //test = solution.addOperators("00",0);
    //test = solution.addOperators("232",8);
    //test = solution.addOperators("3456237490",9191);
    test = solution.addOperators("1000000009",9);

    for (const auto &item : test){
        cout << item << endl;
    }
}