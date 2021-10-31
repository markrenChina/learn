//
// Created by mark on 2021/10/7.
//
#include <iostream>
#include <vector>
#include <unordered_map>
using namespace std;

    int lengthOfLongestSubstring(string s) {
        vector<unordered_map<char,int>> vector;
        unordered_map<char,int> temp;
        int record = 0;
        for (int i = 0; i< s.size(); i++) {
            auto ret = temp.insert(move(make_pair(s[i],i)));
            if (!ret.second){
                for (int j = record - 1; j >= 0; --j) {
                    auto retPre = temp.insert(move(make_pair(s[j],j)));
                    if (!retPre.second){
                        break;
                    }
                }
                vector.push_back(temp);
                i = temp.at(s[i]);
                temp.clear();
                //temp.insert(s[i]);
                //record = i;
                record = i + 1;
            }
        }
        for (int j = record - 1; j >= 0; --j) {
            auto retPre = temp.insert(move(make_pair(s[j],j)));
            if (!retPre.second){
                break;
            }
        }
        vector.push_back(temp);
        int max = 0;
        for (auto & i : vector) {
            if (max < i.size()){
                max = (int)i.size();
            }
        }
        return max;
    }


    int main(){
        cout << lengthOfLongestSubstring("dvdf") << endl; //2
        cout << lengthOfLongestSubstring("abcabcbb") << endl; //3
        cout << lengthOfLongestSubstring("bpoiexpqhmebhhu") << endl;//oiexpqhm  //8
    }