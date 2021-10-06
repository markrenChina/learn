//
// Created by mark on 2021/9/30.
//
#include <iostream>
#include <vector>
#include <unordered_set>

using namespace std;
    bool containsDuplicate(vector<int>& nums){
        /*unordered_set<int> hashtable;
        for (auto& e :nums) {
            auto ret = hashtable.insert(e);
            if (!ret.second){
                return true;
            }
        }
        return false;*/
        int* array = (int*)malloc(sizeof(int)*INT16_MAX);
        for (auto& e :nums){
            if (1 == array[e] ){
                return true;
            } else {
                array[e] = 1;
            }
        }
        free(array);
        return false;
    }

int main(){
    //vector<int> ve1{ 1,2,3,1 };
    vector<int> ve2{ 1,2,3,4 };
    //cout << containsDuplicate(ve1) << endl;
    cout << containsDuplicate(ve2) << endl;
}
