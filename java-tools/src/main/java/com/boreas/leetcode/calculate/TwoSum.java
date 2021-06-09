package com.boreas.leetcode.calculate;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 * @author boreas
 * @create 2021-05-09 22:27
 */
public class TwoSum {
    /**
     * 哈希表
     * @param nums
     * @param target
     * @return
     */
    public int[] solutionHash(int[] nums, int target) {
        Map<Integer, Integer> hashtable  = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashtable .containsKey(target - nums[i])) {
                return new int[]{hashtable .get(target - nums[i]), i};
            }
            hashtable .put(target - nums[i], i);
        }
        return new int[0];
    }

    /**
     * 暴力枚举
     * @param nums
     * @param target
     * @return
     */
    public int[] solutionTwo(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
