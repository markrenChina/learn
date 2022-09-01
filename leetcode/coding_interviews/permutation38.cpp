//
// Created by Ushop on 2022/8/10.
//
#include "../leetcode-utils.hpp"

class Solution {
public:
    vector<string> rec;
    vector<int> vis;

    void backtrack(const string& s, int i, int n, string& perm) {
        if (i == n) {
            rec.push_back(perm);
            return;
        }
        for (int j = 0; j < n; j++) {
            //输入 aaa举例 aaa被保存后弹出最后一个a vis变为 110
            //再弹出最后一个a vis 变为100 此时j=2 按照abc应该取c
            //但是 !vis[1]（前一个没被访问） && s[1] == s[2]（前一个字符与后一个一样） continue
            if (vis[j] || (j > 0 && !vis[j - 1] && s[j - 1] == s[j])) {
                continue;
            }
            vis[j] = true;
            perm.push_back(s[j]);
            backtrack(s, i + 1, n, perm);
            //弹出最后一个字符 a   => ab => abc => ab  => a   => ac => acb
            //输入 aaa举例     a   => aa => aaa
            perm.pop_back();
            //vis值得变化：   100 => 110 => 111 => 110 => 100 => 101 => 111
            //最后一个字符访问位为false
            vis[j] = false;
        }
    }

    vector<string> permutation(string s) {
        int n = s.size();
        vis.resize(n);
        sort(s.begin(), s.end());
        string perm;
        backtrack(s, 0, n, perm);
        return rec;
    }
};

int main(){
    Solution solution;
    solution.permutation("aaa");
}