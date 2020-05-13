package com.boreas.datastructures;
/**
 * 排序算法
 * 日常操作中常见的排序方法有：冒泡排序、快速排序、选择排序、插入排序、希尔排序，
 * 甚至还有基数排序、鸡尾酒排序、桶排序、鸽巢排序、归并排序等。
 * @author jiangxvhua
 * @date 创建时间  2017年2月8日 下午3:30:23
 */
public class BaseSort {

	public BaseSort() {}
	
	/**  
	 * 冒泡法排序<br/>
	 * 
	 * 冒泡排序是一种简单的排序算法。
	 * 它重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。
	 * 走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。
	 * 这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。
	 * 
	 * <li>比较相邻的元素。如果第一个比第二个大，就交换他们两个。</li>
	 * <li>对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。</li>
	 * <li>针对所有的元素重复以上的步骤，除了最后一个。</li>
	 * <li>持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。</li>  
	 * @param numbers 需要排序的整型数组  
	 */  
	public static void bubbleSort(int[] numbers) {
	    int temp;                  // 记录临时中间值
	    int size = numbers.length; // 数组大小
	    for (int i = 0; i < size - 1; i++) {
	        for (int j = i + 1; j < size; j++) {  // 取两个数，从0开始
	        	// 交换两数的位置，可从小到大或从大到小
	            if (numbers[i] < numbers[j]) {
	                temp = numbers[i];
	                numbers[i] = numbers[j];
	                numbers[j] = temp;
	            }
	        }
	    }
	}
	
	/**
	 * 插入排序<br/>
	 * 时间复杂度：O（n^2）.
	 * 插入排序的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，
	 * 找到相应位置并插入。其具体步骤参见代码及注释。
	 * 插入排序的最坏时间复杂度为 O(n^2)，属于稳定排序算法，对于处理小批量数据时高效，因此JAVA在排序元素个数小于7时，选择了这种算法。
	 * 
	 * <ul>
	 * <li>从第一个元素开始，该元素可以认为已经被排序</li>
	 * <li>取出下一个元素，在已经排序的元素序列中从后向前扫描</li>
	 * <li>如果该元素（已排序）大于新元素，将该元素移到下一位置</li>
	 * <li>重复步骤3，直到找到已排序的元素小于或者等于新元素的位置</li>
	 * <li>将新元素插入到该位置中</li>
	 * <li>重复步骤2</li>
	 * </ul>
	 * 
	 * @param numbers
	 */
	public static void insertSort(int[] numbers) {
		int temp;                  // 记录临时中间值
		int size = numbers.length; // 数组大小
		int j;
		for (int i = 1; i < size; i++) {
			temp = numbers[i];
			// 若第i个元素大于i-1元素，直接插入。小于的话，移动有序表后插入
			for (j = i; j > 0 && temp < numbers[j - 1]; j--) {
				numbers[j] = numbers[j - 1];  // 从小到大排
			}				
			numbers[j] = temp;
		}
	}
	
	/**  
	 * 快速排序<br/>
	 * 
	 * 快速排序使用分治法策略来把一个序列分为两个子序列。
	 * 首先任意选取一个数据（通常选用数组的第一个数）作为关键数据，
	 * 然后将所有比它小的数都放到它前面，所有比它大的数都放到它后面，这个过程称为一趟快速排序。
	 * 
	 * <ul>
	 * <li>从数列中挑出一个元素，称为“基准”</li>
	 * <li>重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。
	 *     在这个分割之后， 该基准是它的最后位置。这个称为分割（partition）操作。</li>
	 * <li>递归地把小于基准值元素的子数列和大于基准值元素的子数列排序。</li>
	 * </ul>
	 *   
	 * @param numbers 需要排序的整型数组
	 * @param start
	 * @param end
	 */  
	public static void quickSort(int[] numbers, int start, int end) {
		if (start < end) {
			int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
			int temp;                  // 记录临时中间值
			int i = start, j = end;
	        do {
				while ((numbers[i] < base) && (i < end))
					i++;
				while ((numbers[j] > base) && (j > start))
					j--;
				if (i <= j) {
					temp = numbers[i];
					numbers[i] = numbers[j];
					numbers[j] = temp;
					i++;
					j--;
				}
			} while (i <= j);       
			if (start < j) {
				quickSort(numbers, start, j);			
			}
			if (end > i) {
				quickSort(numbers, i, end);			
			}			
	    }
	}
	/**  
	 * 选择排序<br/>
	 * 
	 * 选择排序是一种简单直观的排序方法，每次寻找序列中的最小值，然后放在最末尾的位置。
	 * 
	 * <li>在未排序序列中找到最小元素，存放到排序序列的起始位置</li>
	 * <li>再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。</li>
	 * <li>以此类推，直到所有元素均排序完毕。</li>
	 * @param numbers
	 */  
	public static void selectSort(int[] numbers) {
		int temp;                  // 记录临时中间值
		int size = numbers.length; // 数组大小
		for (int i = 0; i < size; i++) {
			int k = i;
			for (int j = size - 1; j > i; j--) {  //一次循环把最小值放在起始位置
				if (numbers[j] < numbers[k])
					k = j;
			}
			temp = numbers[i];
			numbers[i] = numbers[k];
			numbers[k] = temp;
		}
	}
	
	
	
	/**  
	 * 归并排序<br/>
	 * 
	 * 归并排序是建立在归并操作上的一种有效的排序算法，归并是指将两个已经排序的序列合并成一个序列的操作。
	 * （1）“分解”——将序列每次折半划分。
	 * （2）“合并”——将划分后的序列段两两合并后排序。
	 * 
	 * <ul>
	 * <li>申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列</li>
	 * <li>设定两个指针，最初位置分别为两个已经排序序列的起始位置</li>
	 * <li>比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置</li>
	 * <li>重复步骤3直到某一指针达到序列尾</li>
	 * <li>将另一序列剩下的所有元素直接复制到合并序列尾</li>
	 * </ul>
	 *   
	 * @param numbers
	 */
	public static void mergeSort(int[] numbers, int left, int right) {
	    int t = 1;// 每组元素个数
	    int size = right - left + 1;
	    while (t < size) {
	        int s = t;// 本次循环每组元素个数
	        t = 2 * s;
	        int i = left;
	        while (i + (t - 1) < size) {
	            merge(numbers, i, i + (s - 1), i + (t - 1));
	            i += t;
	        }
	        if (i + (s - 1) < right)
	            merge(numbers, i, i + (s - 1), right);
	    }
	}
	/**  
	 * 归并算法实现
	 * 
	 * @param data
	 * @param p
	 * @param q
	 * @param r
	 */
	private static void merge(int[] data, int p, int q, int r) {
	    int[] B = new int[data.length];
	    int s = p;
	    int t = q + 1;
	    int k = p;
	    while (s <= q && t <= r) {
	        if (data[s] <= data[t]) {
	            B[k] = data[s];
	            s++;
	        } else {
	            B[k] = data[t];
	            t++;
	        }
	        k++;
	    }
		if (s == q + 1) {
			B[k++] = data[t++];
		} else {
			B[k++] = data[s++];
		}
		for (int i = p; i <= r; i++) {
			data[i] = B[i];
		}
	}
	
	public static void outputArray(int[] numbers){
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + ",");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		int[] numbers = {33,244,24,423,54,34,11,1,2,3,4,5,4,3,2,79,76,90,8};
		int start = 0;
		int end = numbers.length - 1;
		BaseSort.quickSort(numbers, start, end);
	}

}
