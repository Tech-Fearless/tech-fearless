package com.tech.fearless.boot.algorithm;

import java.util.HashMap;
import java.util.Map;

public class Sum {

    /**
     * 题目：
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     *
     * 链接：https://leetcode-cn.com/problems/two-sum
     */
    public int[] twoSumMethodOne(int[] nums, int target) {
        for (int i = 0;i < nums.length; i++) {
            for (int j = i + 1;j < nums.length; j++) {
                if(nums[i] + nums[j] == target) {
                    return new int[]{i,j};
                }
            }
        }
        return new int[2];
    }

    public int[] twoSumMethodTwo(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();

        for (int i = 0;i < nums.length ; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0;i < nums.length; i++) {
            if ((map.get(target-nums[i]) != null) &&
                    (i != map.get(target-nums[i]))){
                return new int[]{i,map.get(target-nums[i])};
            }
        }
        return new int[2];
    }

}
