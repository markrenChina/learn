#include <vector>
#include <iostream>
#include <unordered_set>

using namespace std;

    bool containsDuplicate(vector<int> &nums) {
        unordered_set<int> hashtable;
        for (auto &e :nums) {
            auto ret = hashtable.insert(e);
            if (!ret.second) {
                return true;
            }
        }
        return false;
    }

    bool containsNearbyDuplicate(vector<int> &nums, int k) {
        //assert(k >= 0);
        if (nums.size() < 2 || k <= 0) {
            return false;
        }
        if (nums.size() < k ) {
            //利用哈希表
            return containsDuplicate(nums);
        }
        //获得一个比对hashtable
        unordered_set<int> hashtable;
        for (int i = 0; i < k; ++i){
            auto ret = hashtable.insert(nums[i]);
            if (!ret.second) {
                return true;
            }
        }
        //跟hashtable比对
        for (int i = k; i < nums.size(); ++i) {
            auto ret = hashtable.insert(nums[i]);
            if (!ret.second) {
                return true;
            }
            hashtable.erase(nums[i-k]);
        }
        return false;
    }


int main() {
    vector<int> vec1 = {1, 2, 3, 1};
    vector<int> vec2 = {1, 0, 1, 1};
    vector<int> vec3 = {1, 2, 3, 1, 2, 3};
    vector<int> vec4 = {1};

    cout << containsNearbyDuplicate(vec1, 3) << endl;
    cout << containsNearbyDuplicate(vec2, 1) << endl;
    cout << containsNearbyDuplicate(vec3, 2) << endl;
    cout << containsNearbyDuplicate(vec4, 3) << endl;
}