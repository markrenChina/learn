//
// Created by Ushop on 2022/7/13.
//
#include "leetcode-utils.hpp"
#include <stack>
/**
 * 1. 所有的正数会碰撞右边绝对值+角标差小于自身的
 * 2. 所有的负数会碰撞左边自身绝对值+角标差小于目标绝对值的
 * 3. 所有的正数+负数大于0的都会发生碰撞
 * 举例： 3 4 6 -5 ，4 -5 会因为3消除4，而因为1或2，4 其实保留了
 *
 * 栈模拟，使用栈 模拟行星碰撞，从左往右遍历行星数组 asteroids，当我们遍历到行星aster 时，使用变量 alive 记录行星 aster 是否还存在（即未爆炸）。
 * 当行星 aster 存在且 aster<0，栈顶元素非空且大于 0 时，说明两个行星相互向对方移动：如果栈顶元素大于等于 −aster，则行星 aster 发生爆炸，
 * 将 alive 置为 false；如果栈顶元素小于等于 −aster，则栈顶元素表示的行星发生爆炸，执行出栈操作。
 * 重复以上判断直到不满足条件，如果最后 alive 为真，说明行星 aster 不会爆炸，则将 aster 入栈。
 */
class Solution {
public:
    vector<int> asteroidCollision(vector<int>& asteroids) {
        vector<int> st;
        for (auto aster : asteroids) {
            bool alive = true;
            while (alive && aster < 0 && !st.empty() && st.back() > 0) {
                alive = st.back() < -aster; // aster 是否存在
                if (st.back() <= -aster) {  // 栈顶行星爆炸
                    st.pop_back();
                }
            }
            if (alive) {
                st.push_back(aster);
            }
        }
        return st;
    }
};


int main(){
    Solution solution;
    vector<int> vec{3,4,6,-5};
    vector<int> bk = solution.asteroidCollision(vec);
    for_each(bk.begin(), bk.end(),[](int& i){cout << i << " "; });
}
