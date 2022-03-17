package com.boreas.leetcode.string;

import java.util.Deque;
import java.util.LinkedList;

/**
 *给出一个字符串 s（仅含有小写英文字母和括号）。
 * 请你按照从括号内到外的顺序，逐层反转每对匹配括号中的字符串，并返回最终的结果。
 * 注意，您的结果中 不应 包含任何括号。
 *
 * 示例 1：
 * 输入：s = "aa(aa(abc(ve)d)cdf)"
 * 输出："dcba"
 * @author boreas
 * @create 2022-03-16 17:41
 */
public class ReverseParentheses {

    public static void main(String[] args) {
        ReverseParentheses.reverseParentheses("aa(aa(abc(ve)d)cdf)");
    }

    public static String reverseParentheses(String s) {
        Deque<String> stack = new LinkedList<String>();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                // 将 str 插入到栈中，并将 str 置为空，进入下一层
                stack.push(sb.toString());
                sb.setLength(0);
            } else if (c == ')') {
                // 如果是右括号，则说明遍历完了当前层，需要将 str 反转，返回给上一层。
                // 具体地，将栈顶字符串弹出，然后将反转后的 str 拼接到栈顶字符串末尾，将结果赋值给 str。
                sb.reverse();
                sb.insert(0, stack.pop());
            } else {
                // 小写英文字母，将其加到 str 末尾
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
