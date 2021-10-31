//
// Created by mark on 2021/10/31.
//
#include <iostream>
#include <vector>

using namespace std;

class Solution {

    const string first_line = "qwertyuiop";
    const string second_line = "asdfghjkl";
    const string thrid_line = "zxcvbnm";

    string whichLine(const string &word){
        if (isInLine(second_line,word[0])){
            return second_line;
        }
        if (isInLine(thrid_line,word[0])){
            return thrid_line;
        } else{
            return first_line;
        }
    }

    bool isInLine(const string& line,const char c){
        char target;
        if (c <= 'Z'){
            target = c - 'A' + 'a';
        } else{
            target = c;
        }
        return line.find(target) + 1;
    }


public:
    vector<string> findWords(vector<string> &words) {
        vector<string> res;
        string temp;
        for(auto& word : words){
            temp = whichLine(word);
            for (int i = 1; i < word.size(); ++i) {
                if (!isInLine(temp,word[i])){
                    goto finnish;
                }
            }
            res.push_back(word);
            finnish:;
        }
        return res;
    }
};

int main(){
    //vector<string> words = { "Hello","Alaska","Dad","Peace" };
    //vector<string> words = { "omk" };
    vector<string> words = { "adsdf","sfd" };
    Solution solution;
    vector<string> res = solution.findWords(words);
    for (const auto& e: res) {
        cout << e << " ";
    }
}