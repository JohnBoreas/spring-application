package com.boreas.arithmetic;

/**
 * 快速排序-分治算法
 */
public class QuickSort {

    public void sort(int[] arrays) {
        sort(arrays, 0, arrays.length);
    }

    public void sort(int[] arrays, int start, int end) {
        if (start < end) {
            int pos = pass(arrays, start, end);// 划分两个子表
            sort(arrays, start, pos -1);// 对左子表快排
            sort(arrays, pos + 1, end);// 对右子表快排
        }
    }
    /**
     * 一趟快速排序算法
     * @param arrays 待排序数组
     * @param start 数组开始下标
     * @param end 数组结束下标
     * @return
     */
    public int pass(int[] arrays, int start, int end) {
        int temp = arrays[start];
        while (start < end) {
            while (start < end && arrays[end] >= temp) {// 从右向左扫描，找到第一个小于temp的值
                end--;
            }
            if (start < end) {// 将此值放入下标为low的数组中
                arrays[start] = arrays[end];
                start++;
            }
            while (start < end && arrays[start] <= temp) {// 从左向右扫描找到第一个大于temp的值
                start++;
            }
            if (start < end) {
                arrays[end] = arrays[start];// 放在下标为high的数组中
                end--;
            }
        }
        arrays[start] = temp;// 将此元素放入其准确位置
        return start;// 返回此次排序完成的值当前下标（即此位置的元素已经排好）
    }
}
