package com.boreas.arithmetic;

/**
 * 归并排序--分治算法
 */
public class MergeSort {
    /**
     * 将arr[low..mid]和arr[mid + 1..high]合并为一个有序序列
     * @param arr
     * @param low
     * @param mid
     * @param high
     */
    public static void merg(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low, j = mid + 1, k = 0;
        while (i <= mid && j <= high) {// 总是将两个序列中较小的赋值给当前数组元素
            if (arr[i] <= arr[j]) {
                temp[k] = arr[i];
                k++;
                i++;
            } else {
                temp[k] = arr[j];
                k++;
                j++;
            }
        }
        while (i <= mid) {// 如果一个序列遍历完，则将另一个序列所有元素复制到当前数组
            temp[k] = arr[i];
            k++;
            i++;
        }
        while (j <= high) {
            temp[k] = arr[j];
            j++;
            i++;
        }
        System.arraycopy(temp, 0, arr, low, temp.length);// 将合并后的序列重新复制给arr
    }

    /**
     * @param arr  待排序数组
     * @param low  开始下标
     * @param high 结尾下标
     */
    public static void sort(int[] arr, int low, int high) {
        if (low == high)                // 如果low等于high说明排序进行完成
            return;
        else {
            int mid = (low + high) / 2;
            sort(arr, low, mid);        // 拆分左边
            sort(arr, mid + 1, high);    // 拆分右边
            merg(arr, low, mid, high);    // 排序合并
        }
    }
}
