package com.boreas.leetcode.array;

/**
 * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 * 假设你总是可以到达数组的最后一个位置。
 * 示例 1:
 *
 * 输入: nums = [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
 * @author boreas
 * @create 2022-03-16 16:12
 */
public class JumpGame {
    /**
     * 贪心算法
     */
    public int jump(int[] nums) {
        int jumpCount = 0;
        int index = 0;
        while (index < nums.length - 1) {
            int step = nums[index];// 跳多少格
            int maxCount = 0;// 最大能跳几个
            int nextIndex = index;// 下一个位置
            for (int i = index + 1; i < nums.length && i < step + index; i ++) {
                if (i == nums.length - 1) {
                    jumpCount ++;
                    return jumpCount;
                }
                int count = i + nums[i];// 计算当前最远能跳多少
                if (count > maxCount) {
                    maxCount = count;
                    nextIndex = i;
                }
            }
            index = nextIndex;
            jumpCount ++;
        }
        return jumpCount;
    }
}
