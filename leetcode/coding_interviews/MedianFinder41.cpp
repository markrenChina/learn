//
// Created by Ushop on 2022/8/4.
//
//建立2个堆，一个最小堆，一个最大堆
//加入的数先放入 最小堆（中位数的右边），再取最小值放入最大堆，来平衡个数（最小堆允许个数大1）
//奇数时取最小堆，偶数时取两个各取有个/2

#include "../leetcode-utils.hpp"

class MedianFinder {
private:
    deque<int> minHeap;
    int minSize = 0;
    vector<int> maxHeap;
    void swapMaxMin(){
        int min = minHeap[0];
        int max = maxHeap[0];
        pop_heap(minHeap.begin(), minHeap.begin()+minSize,greater<>());
        pop_heap(maxHeap.begin(), maxHeap.end(),less<>());
        maxHeap[maxHeap.size() - 1] = min;
        minHeap[minSize - 1] = max;
//        ::swap(minHeap[0],maxHeap[0]);
        push_heap(minHeap.begin(), minHeap.begin()+minSize,greater<>());
        push_heap(maxHeap.begin(),maxHeap.end(),less<>());
    }
public:
    /** initialize your data structure here. */
    MedianFinder() {
        make_heap(minHeap.begin(), minHeap.begin()+minSize,greater<>());
        make_heap(maxHeap.begin(), maxHeap.end(),less<>());
    }

    void addNum(int num) {
        minHeap.push_back(num);
        minSize++;
        push_heap(minHeap.begin(), minHeap.begin()+minSize,greater<>());
    }

    double findMedian() {
        size_t size = minSize + maxHeap.size();
        if (size == 0){
            return 0.0;
        }
        double mid = 0.0;
        if ((size % 2) == 1){
            while (minSize != (maxHeap.size()+1)){
                maxHeap.push_back(minHeap[0]);
                push_heap(maxHeap.begin(),maxHeap.end(),less<>());
                pop_heap(minHeap.begin(), minHeap.begin()+minSize,greater<>());
                minSize--;
            }
            if (!maxHeap.empty()){
                while (minHeap[0] < maxHeap[0]){
                    swapMaxMin();
                }
            }
            mid = (double ) minHeap[0];
        }else {
            while (maxHeap.size() != minSize){
                maxHeap.push_back(minHeap[0]);
                push_heap(maxHeap.begin(),maxHeap.end(),less<>());
                pop_heap(minHeap.begin(), minHeap.begin()+minSize,greater<>());
                //minHeap.pop_back();
                minSize--;
            }
            while (minHeap[0] < maxHeap[0]){
                swapMaxMin();
            }
            mid = ((double ) minHeap[0] + (double )maxHeap[0]) /2;
        }
        return mid;
    }
};

int main(){
    MedianFinder medianFinder;
    medianFinder.addNum(-1);
    cout << medianFinder.findMedian() << " ";
    medianFinder.addNum(-2);
    cout << medianFinder.findMedian() << " ";
    medianFinder.addNum(-3);
    cout << medianFinder.findMedian() << " ";
    medianFinder.addNum(-4);
    cout << medianFinder.findMedian() << " ";
    medianFinder.addNum(-5);
    cout << medianFinder.findMedian() << " ";

}