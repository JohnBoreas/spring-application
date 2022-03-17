package com.boreas.leetcode.array;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给定一个整数数组temperatures，表示每天的温度，返回一个数组answer，
 * 其中answer[i]是指在第 i 天之后，才会有更高的温度。如果气温在这之后都不会升高，请在该位置用0 来代替。
 *
 * 示例 1:
 * 输入: temperatures = [73,74,75,71,69,72,76,73]
 * 输出:[1,1,4,2,1,1,0,0]
 * @author boreas
 * @create 2022-03-17 22:37
 */
public class DailyTemperatures {

    public static void main(String[] args) {
        DailyTemperatures.dailyTemperatures(new int[]{23, 24, 25, 21, 26, 27, 30, 23});
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] res = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            int temperature = temperatures[i];
            // 当每日温度大于栈顶温度，则为升温日，出栈，计算与当天温度的差，存入
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperature) {
                int prevIndex = stack.pop();
                res[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return res;
    }
}
