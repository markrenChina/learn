/**
 * 利用二分查找思想，因为是有序旋转
 * 可以分3种情况：
 * 1. 本身有序，例如 1,2,3,4,5,6 直接返回a[0]
 * 2. a[n-1] == a[0] == a[mid] 无法判断在前子组还是后子组，退化为遍历
 * 3. a[n-1] < a[0] 通过 a[mid] > a[n-1] 最小值在后子组，<等于 最小值在前子组,
 */
#include "../leetcode-utils.hpp"

class Solution {
public:
    int minArray(vector<int> &numbers) {
        if (numbers.size() == 1) {
            return numbers[0];
        }
        int lastIndex = numbers.size() - 1;
        int startIndex = 0;
        int midIndex = 0;
        if (startIndex == lastIndex){
            return numbers[0];
        }
        while (numbers[startIndex] >= numbers[lastIndex]) {
            if (lastIndex - startIndex == 1){
                midIndex = lastIndex;
                break;
            }
            midIndex = ((lastIndex - startIndex) >> 1) + startIndex;
            if (numbers[startIndex] == numbers[midIndex] && numbers[midIndex] == numbers[lastIndex]){
                //重复会进入
                int min = INT32_MAX;
                for (int i = startIndex; i <= lastIndex; ++i) {
                    min = ::min(numbers[i], min);
                }
                return min;
            } else {
                if (numbers[midIndex] > numbers[lastIndex]) {
                    startIndex = midIndex ;
                } else {
                    lastIndex = midIndex ;
                }
            }
        }
        return numbers[midIndex];
    }
};

int main() {
    Solution solution;
    vector<int> vec = {3,1,3};
    vector<int> vec2 = {5,1,3};
    cout << solution.minArray(vec) << endl; //1
    cout << solution.minArray(vec2) << endl; // 1
}