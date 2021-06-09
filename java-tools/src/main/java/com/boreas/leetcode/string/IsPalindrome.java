package com.boreas.leetcode.string;

/**
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 *
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * 示例 1:
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 *
 * 示例 2:
 * 输入: "race a car"
 * 输出: false
 */
public class IsPalindrome {

    public boolean solution(String s) {
        if (s.length() == 0) {
            return true;
        }
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {// 字符被确定是字母或数字
                left ++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right --;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left ++;
            right --;
        }
        return true;
        // String actual = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        // String rev = new StringBuffer(actual).reverse().toString();
        // return actual.equals(rev);
    }
}
