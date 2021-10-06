//
// Created by mark on 2021/10/3.
//
#include <iostream>
#include <vector>

using namespace std;

    struct Status {
        int lSum; //lSum 表示 [l,r] 内以 l 为左端点的最大子段和
        int rSum; //rSum 表示 [l,r] 内以 r 为右端点的最大子段和
        int mSum; //mSum 表示 [l,r] 内的最大子段和
        int iSum; //iSum 表示 [l,r] 的区间和
    };

    Status maxSubArray(vector<int>& nums, int l, int r){
        if (l == r) {
            return Status{ nums[l],nums[l],nums[l],nums[l] };
        }
        int mid = (l + r ) >> 1;
        //分治左部分最大值
        Status left = maxSubArray(nums,l,mid);
        //分治右部分最大值
        Status right = maxSubArray(nums,mid + 1,r);
        //核心求解算法
        int iSum = left.iSum + right.iSum; //区间和
        int lSum = max(left.lSum, left.iSum + right.lSum);//靠左边的最大值
        int rSum = max(right.rSum,right.iSum + left.rSum);//靠右边的最大值
        //左边最大值，右边最大值，左边靠右边最大值+右边靠左边最大值的最大值
        int mSum = max({left.mSum,right.mSum,left.rSum + right.lSum});
        return Status {lSum,rSum,mSum,iSum};
    }

    int maxSubArray(vector<int>& nums) {
        //分治
        //return maxSubArray(nums,0,nums.size()-1).mSum;
        //动态规划
        int sum = 0;
        //int pre = 0;
        int res = nums[0];
        for (int &num : nums) {
            //res = max(res,sum+=num);
            //sum = max(0,sum);
            //pre = max(pre + num,num);
            //res = max(res,pre);
            if(sum<0){
                sum=num;
            }
            else{
                sum+=num;
            }
            if(sum>res){
                res=sum;
            }
        }
        return res;
    }

int main(){
    vector<int> v1 = {-2,1,-3,4,-1,2,1,-5,4};

    cout << maxSubArray(v1) <<endl;
    return 0;
}
