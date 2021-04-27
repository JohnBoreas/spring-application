package com.boreas.leetcode.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 多数元素
 *
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 示例 1：
 * 输入：[3,2,3]
 * 输出：3
 *
 * 示例 2：
 * 输入：[2,2,1,1,1,2,2]
 * 输出：2
 *
 * @author xuhua.jiang
 * @date 2021-04-06
 */
public class MajorityElement {
    /**
     * 摩尔投票：
     * 众数+1， 非众数-1，一定有所有的票数和>0；
     * 若前a个数字的票数和为0，剩余n-a个数字的票数和一定仍大于0，后面n-a的众数仍为x
     * @param nums
     * @return
     */
    public int majorityElementOne(int[] nums) {
        int count = 0;
        int num = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == num) {
                count ++;
            } else {
                count --;
                if (count == 0) {
                    num = nums[i];
                    count ++;
                }
            }
        }
        return num;
    }

    /**
     * 遍历，用Map记录每个数字出现次数，最后再遍历一遍找出次数大于n/2的的元素
     * @param nums
     * @return
     */
    public int majorityElementTwo(int[] nums) {
        Map<Integer, Integer> numCountMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int count = numCountMap.getOrDefault(nums[i], 0) + 1;
            //如果某个数字出现的个数已经超过数组的一半，自己返回
            if (count > nums.length / 2) {
                return nums[i];
            }
            numCountMap.put(nums[i], count);
        }
        return -1;
    }
}
