//
// Created by mark on 2022/7/3.
//
#include <vector>
#include <iostream>
#include <cstdlib>
#include <deque>

using namespace std;

class Solution {
    deque<int> numbers;

public:
    int nextGreaterElement(int n) {
        if (n < 10){
            return  -1;
        }
        number2vector(n);
        sort();
        //printNumbers();
        long res = 0;
        vector2Number(res);
        //cout << res;
        if (res > INT32_MAX) {
            return -1;
        }
        if (res <= n){
            res = -1;
        }
        return (int)res;
    }

private:
    static int comp(const void * left , const void* right){
        return *(int*)left - *(int*)right;
    }

    void sort(){
        vector<int> vec;
        vec.push_back(numbers.at(numbers.size()-1));
        for (int i = (int )numbers.size() -2 ; i >= 0; --i) {
            vec.push_back(numbers.at(i));
            if (numbers.at(i) < numbers.at(i + 1)){
                qsort((void*)&vec[0],vec.size(),sizeof (int),comp);
                int j=0;
                for (;j<vec.size();j++) {
                    if (vec[j] > numbers[i]){
                        numbers[i] = vec[j];
                        break;
                    }
                }
                for (int k = 0; k < vec.size() && i+1+k < numbers.size(); ++k) {
                    if (k < j){
                        numbers[i+1+k] = vec[k];
                    } else{
                        numbers[i+1+k] = vec[k+1];
                    }

                }
                vec.clear();
                return;
            }
        }
        vec.clear();
    }

    void printNumbers(){
        for(auto& e: numbers ){
            cout << e << " ";
        }
    }

    void number2vector(int n){
        while (n != 0){
            numbers.push_front(n%10);
            n/=10;
        }
    }

    void vector2Number(long& res){
        for(auto& e: numbers ){
            res *= 10;
            res += e;
        }
    }

};


int main(){
    Solution solution;
    cout << solution.nextGreaterElement(101);
    return 0;
}