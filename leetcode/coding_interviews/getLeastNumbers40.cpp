//
// Created by Ushop on 2022/8/2.
//
#include "../leetcode-utils.hpp"

//利用快排的思路
class Solution {
private:
    int quickSort(vector<int>& arr,int start, int end){
        int pirot = arr[end];
        int i = start;
        for(int j=start; j < end; j++){
            if(arr[j] < pirot) {
                ::swap(arr[i],arr[j]);
                i++;
            }
        }
        ::swap(arr[i],arr[end]);
        return i;
    }

public:
    vector<int> getLeastNumbers(vector<int>& arr, int k) {
        vector<int> res;
        if( k == 0){
            return res;
        }
        int start = 0;
        int end = arr.size()-1;
        int index = quickSort(arr,0,arr.size()-1);
        while(index != (k-1)){
            if(index < (k-1)){
                start = index + 1;
                index = quickSort(arr,start,end);
            }else {
                end = index -1;
                index = quickSort(arr,start,end);
            }
        }
        res.resize(k);
        for(int i=0 ; i<k; ++i){
            res[i] = arr[i];
        }
        return res;
    }
};

int main(){
    Solution solution;
//    vector<int> vec {0,0,1,3,4,5,0,7,6,7};
    vector<int> vec {0,0,2,0,5};
//    vector<int> vec {3,2,1};
    auto res = solution.getLeastNumbers(vec,0);
    cout<< res[0] ;
}