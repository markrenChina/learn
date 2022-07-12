//
// Created by mark on 2022/7/7.
//
#include <string>
#include <vector>
#include <unordered_map>
#include <stdexcept>
#include <sstream>
#include <iostream>

using namespace std;


struct Node{
    //一个链表链接的哈希表
    unordered_map<char,Node*> next;
};

class Solution {
public:
    string replaceWords(vector<string>& dictionary, string sentence) {
        //生成表
        for (auto& word: dictionary) {
            //判断单词的前缀和已有的一样，选择更短的在里面
            Node* node = &dicTree;
            bool needAdd = true;
            for (auto& c: word) {
                if (node->next.find(c) != node->next.end()){
                    //找到
                    needAdd = false;
                    node = node->next.at(c);
                }else if (node == &dicTree){
                    break;
                }
            }
            //长单词已记录，短单词需要重新记录，然后断开next变短
            //举例 catt已有，再加入cat
            needAdd = needAdd || !node->next.empty();
            node = &dicTree;
            if (needAdd){
                for (auto& c: word) {
                    if (node->next.find(c) != node->next.end()){
                        node = node->next.at(c);
                    } else{
                        Node* tmp = new Node();
                        node->next[c] = tmp;
                        node = tmp;
                    }
                }
                //还有证明是长单词，清理掉（已有aa，插入a，aa要清理）
                if (!node->next.empty()){
                    node->next.clear();
                }
            }
        }

        //查表
        Node* root = &dicTree;
        stringstream os;
        bool append = true;
        for (int i = 0; i < sentence.size();++i) {
            char c = sentence[i];
            if (c != ' ' && append){
                try {
                    root = root->next.at(c);
                    os << c;
                }catch (out_of_range& e){
                    //词根到头，后面不拼接
                    if (root->next.empty()) {
                        append = false;
                    } else  {
                        //不带词根的单词直接完整拼接
                        os << c;
                        i++;
                        for (; i < sentence.size(); ++i) {
                            if (sentence[i] != ' '){
                                os << sentence[i];
                            } else{
                                root = &dicTree;
                                os << ' ';
                                append = true;
                                break;
                            }
                        }
                    }
                }
            } else if (c == ' '){
                root = &dicTree;
                os << ' ';
                append = true;
            }
        }
        return os.str();
    }

private:
    Node dicTree;
};



int main(){

    Solution solution;
    vector<string> vec{"catt","cat","bat","rat"};
    string s = solution.replaceWords(vec,"the cattle was rattled by the battery");
    cout << s << endl;

    Solution solution2;
    vector<string> vec2{"a","b","c"};
    string s2 = solution2.replaceWords(vec2,"aadsfasf absbs bbab cadsfafs");
    cout << s2 << endl;

    Solution solution3;
    vector<string> vec3{"a","aa","aaa"};
    string s3 = solution3.replaceWords(vec3,"a aa aaa aaaa");
    cout << s3 << endl;
}