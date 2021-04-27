package com.boreas.leetcode.base;

/**
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
 * <p>
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 *
 * @author xuhua.jiang
 * @date 2021-04-06
 */
public class SearchMatrix {
    /**
     * 从矩阵的右上角开始找，
     *
     * 1，如果找到就直接返回
     *
     * 2，如果没找到就继续查找
     *      如果查找的值小于target就往下找
     *      如果查找的值大于target就往左找。
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int col = matrix[0].length - 1;
        int row = 0;
        while (col >= 0 && row <= matrix.length - 1) {
            if (target == matrix[row][col]) {
                // 找到返回
                return true;
            } else if (target < matrix[row][col]) {
                // 值大，往左
                col --;
            } else if (target > matrix[row][col]) {
                // 值小，往下
                row ++;
            }
        }
        return false;
    }
}
