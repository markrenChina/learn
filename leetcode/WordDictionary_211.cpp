//
// Created by mark on 2021/10/19.
//
#include <iostream>
#include <vector>
#include <unordered_map>
#include <unordered_set>

using namespace std;

/*class WordDictionary {
private:
    vector<unordered_map<char,unordered_set<char>> *> words;
public:
    WordDictionary() = default;

    void addWord(string word) {
        for (int i = 0; i < word.size(); ++i) {
            if (i >= words.size() ){
                auto *temp = new unordered_map<char,unordered_set<char>>();
                auto temp_set = *new unordered_set<char>();
                temp_set.insert(word[i+1]);
                temp->insert(pair<char,unordered_set<char>>(word[i],temp_set));
                words.push_back(temp);
            } else {
                auto temp = words.at(i)->find(word[i]);
                if (temp == words.at(i)->end()){
                    auto temp_set = *new unordered_set<char>();
                    temp_set.insert(word[i+1]);
                    words.at(i)->insert(pair<char,unordered_set<char>>(word[i],temp_set)) ;
                } else{
                    temp->second.insert(word[i+1]);
                }
            }
        }
    }

    bool search(string word) {
        if (word.size() > words.size()){
            return false;
        }
        for (int i = 0; i < word.size(); ++i) {
            if (word[i] == '.'){
                if (i +1 == word.size()){
                    //遍历map是否有\0结束符
                    for (auto& map_temp: *words.at(i)) {
                        auto find_it = map_temp.second.find('\0');
                        if (find_it != map_temp.second.end()){
                            return true;
                        }
                    }
                    return false;
                }
                continue;
            }
            //差map的set中是否有对应的下一个字母
            auto it = words.at(i)->find(word[i]);
            if (it == words.at(i)->end()){
                return false;
            } else if (word[i+1] != '.'){
                auto it_set = (it->second).find(word[i+1]);
                if (it_set == it->second.end()){
                    return false;
                }
            }
        }
        return true;
    }
};*/

class WordDictionary {
private:
    unordered_set<string> words;
public:
    WordDictionary() {

    };

    void addWord(string word) {
        words.insert(word);
    }

    bool search(string word) {
        for (int i = 0; i < word.size(); ++i) {
            int index = 0;
            while(word[index] == '.'){
                index++;
            }
            for (int e = 0; e < index; ++e) {
                
            }
            if (word[i] == '.'){
                for (int j = 0; j < 26; ++j) {
                    word[i] = 'a' + j;
                    auto it = words.find(word);
                    if (it != words.end()){
                        return true;
                    }
                }
                for (int j = 0; j < 26; ++j) {
                    word[i] = 'A' + j;
                    auto it = words.find(word);
                    if (it != words.end()){
                        return true;
                    }
                }
            }
            i = i+index -1;
        }
        auto it = words.find(word);
        if (it != words.end()){
            return true;
        }
        return false;
    }
};

int main() {
    WordDictionary wordDictionary;
    wordDictionary.addWord("at");
    wordDictionary.addWord("and");
    wordDictionary.addWord("an");
    wordDictionary.addWord("add");
    wordDictionary.addWord("bat");
    cout << wordDictionary.search("b.");
    cout << wordDictionary.search("at");

}