//
// Created by Ushop on 2022/7/16.
//
#include "../leetcode-utils.hpp"

class MovingAverage {
public:
    /** Initialize your data structure here. */
    MovingAverage(int size):m_size(size) {
        queue.resize(size);
    }

    double next(int val) {
        max -= queue[pos];
        queue[pos] = val;
        if (++pos == m_size){
            pos = 0;
            isMax = true;
        }
        max += val;
        if (isMax){
            return max/m_size;
        } else{
            return max/pos;
        }
    }
private:
    vector<int> queue;
    int m_size;
    int pos = 0;
    bool isMax = false;
    double max = 0;
};

int main(){
    MovingAverage* movingAverage = new MovingAverage(3);
    cout << movingAverage->next(1) << endl;
    cout << movingAverage->next(10)<< endl;
    cout << movingAverage->next(3)<< endl;
    cout << movingAverage->next(5)<< endl;
}