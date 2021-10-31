package main.java.leetcode

class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray? {
        //可以优化hashmap
        val map = HashMap<Int,Int>(nums.size)
        var diff = 0
        for(i in nums.indices) {
            if(nums[i] <= target){
                diff = target - nums[i]
                if(map.containsKey(diff)){
                    return intArrayOf(map[diff]!!,i)
                }else{
                    map[nums[i]] = i
                }
            }
        }
        return null
    }
}