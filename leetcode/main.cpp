#include <iostream>
#include <vector>

using namespace std;

int myAtoi(string s) {
    long res = 0;
    bool negative = false;
    //bool isFloat = false;
    bool isBegin = false;
    for (int i = 0; i < s.size(); i++) {
        if ((int) s[i] >= (int) '0' && (int) s[i] <= (int) '9') {
            isBegin = true;

            res = res * 10 + s[i] - (int) '0';
            if (res > INT32_MAX) {
                if (negative) {
                    return INT32_MIN;
                } else {
                    return INT32_MAX;
                }
            }
        } else if (!isBegin) {
            if (s[i] == '-') {
                negative = true;
                isBegin = true;
            } else if (s[i] == '+') {
                isBegin = true;
            } else if (s[i] == ' ') {

            } else {
                if (negative) {
                    res = -res;
                }
                return (int) res;
            }

        } /*else if (s[i] == '.') {
            if (i > 0 && !isFloat && isBegin) {
                isFloat = true;
            } else {
                if (negative) {
                    res = -res;
                }
                return (int)res;
            }
        }*/  else {
            if (negative) {
                res = -res;
            }
            return (int) res;
        }
    }
    if (negative) {
        res = -res;
    }
    return (int) res;
}

int reverse(int x) {
    long res = 0;
    while (x != 0) {
        res = x % 10 + res * 10;
        x = x / 10;
    }
    if (res > INT32_MAX || res < INT32_MIN) {
        return 0;
    }
    return (int) res;
}

int hammingWeight(uint32_t n) {
    int count = 0;
    while (n != 0) {
        //if(n%2 == 1) {
        if ((n & 0x01) == 0x01) {
            count++;
        }
        n = n >> 1;
    }
    return count;
}

int rob(vector<int> &nums) {
    /*if(nums.size() == 0){
        return 0;
    }
    if(nums.size() == 1){
        return nums[0]
    }*/
    /*
    if(nums.size() == 2){
        return max(nums[0],nums[1])
    }*/
    char index = -1;
    int res = 0;
    //条件 1 <= nums.length <= 100
    char end = (char) nums.size() - 1;
    char surplus1 = (char) nums.size() - 2;
    char surplus2 = (char) nums.size() - 3;
    char surplus3 = (char) nums.size() - 4;
    while (index < end) {
        //指针站在index 判断后面哪个可取
        if (index < surplus3) {
            if ((nums[index + 1] + nums[index + 3]) < (nums[index + 2] + nums[index + 4])&& (nums[index + 1]) < (nums[index + 2])
                                                                                            ) {
                //取跨步
                res += nums[index + 2];
                index += 3;
            } else {
                //取相邻
                res += nums[index + 1];
                index += 2;
            }
        } else if (index == surplus1) {
            return res + nums[index + 1];
        } else if (index == surplus2) {
            return res + max(nums[index + 1], nums[index + 2]);
        } else if (index == surplus3) {
            return res + max((nums[index + 1] + nums[index + 3]), nums[index + 2]);
        }
    }
    return res;
}

int main() {
    vector<int> nums = {2, 4, 8, 9, 9, 3};
    int res = rob(nums);
    std::cout << res << std::endl;
    return 0;
}
