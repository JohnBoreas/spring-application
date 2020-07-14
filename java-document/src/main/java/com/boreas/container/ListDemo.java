package com.boreas.container;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListDemo {
	/**
	 * （1）首先是一个数组Object[]，允许null元素，初始容量10
	 * （2）提供了set(int index, E element)：用指定的元素替代此列表中指定位置上的元素，并返回以前位于该位置上的元素。
	 * 		add(E e)、
	 * 		add(int index, E element)：如果当前位置有元素，则向右移动当前位于该位置的元素以及所有后续元素（将其索引加1）。
	 * 		addAll(Collection<? extends E> c)：按照指定collection的迭代器所返回的元素顺序，将该collection中的所有元素添加到此列表的尾部。
	 * 		addAll(int index, Collection<? extends E> c)：从指定的位置开始，将指定collection中的所有元素插入到此列表中。
	 * 		remove(Object o)：移除此列表中首次出现的指定元素（如果存在）。
	 * 	这些添加元素的方法
	 * （3）fail-fast机制：记住是有可能，而不是一定。
	 * 		快速失败迭代器会尽最大努力抛出 ConcurrentModificationException。
	 * 		Java集合的一种错误检测机制。当多个线程对集合进行结构上的改变的操作时，有可能会产生fail-fast机制。
	 * 	解决方案：改变modCount值加上synchronized或者直接使用Collections.synchronizedList===》阻塞
	 * 		         使用CopyOnWriteArrayList来替换ArrayList。推荐使用该方案。
	 */
	private static List<String> arrayList = new ArrayList<>();
	/**
	 * 矢量队列,一个队列，支持相关的添加、删除、修改、遍历等功能，线程安全的
	 * 实现了RandmoAccess接口，即提供了随机访问功能。
	 */
	private static List<String> vector = new Vector<>();
	/**
	 * Stack是栈。特性是：先进后出(FILO, First In Last Out)。
	 * 数组实现的，而非链表,继承于Vector
	 */
	private static List<String> stack = new Stack<String>();
	/**
	 * 双向链表,非同步,继承于AbstractSequentialList，并且实现了Dequeue接口
	 * header是双向链表的表头,size是双向链表中节点的个数
	 * 顺序访问会非常高效，而随机访问效率比较低
	 * 当作 LIFO(后进先出)的堆栈,FIFO(先进先出)的队列
	 */
	private static List<String> linkedList = new LinkedList<String>();
	/**
	 * 任何对array在结构上有所改变的操作（add、remove、clear等），CopyOnWriterArrayList都会copy现有的数据，
	 * 再在copy的数据上修改，这样就不会影响COWIterator中的数据了，修改完成之后改变原有数据的引用即可。
	 * 同时这样造成的代价就是产生大量的对象，同时数组的copy也是相当有损耗的。
	 */
	private static List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

	/**
	 * 测试插入方法（每次都将新增加的元素插入到集合开始处）、注意不要写成 add(Object o)方法、具体原因自己分析
	 */
	private static void testInsert(){
		testInsert(arrayList);
		testInsert(vector);
		testInsert(stack);
		testInsert(linkedList);
		testInsert(copyOnWriteArrayList);
	}
	
	/**
	 * 测试随机访问效率
	 */
	private static void testRandomAccess(){
		testRandomAccess(arrayList);
		testRandomAccess(vector);
		testRandomAccess(stack);
		testRandomAccess(linkedList);
		testRandomAccess(copyOnWriteArrayList);
	}
	
	/**
	 * 测试Iterator迭代效率 
	 */
	private static void testIterator(){
		testIterator(arrayList);
		testIterator(vector);
		testIterator(stack);
		testIterator(linkedList);
		testIterator(copyOnWriteArrayList);
	}
	
	/**
	 * 测试删除效率
	 */
	private static void testDelete(){
		testDelete(arrayList);
		testDelete(vector);
		testDelete(stack);
		testDelete(linkedList);
		testDelete(copyOnWriteArrayList);
	}
	
	private static void testInsert(List<String> list){
		long start = currentTime();
		for (int i = 0; i < 10000; i++) {
			list.add(0,"a");
		}
		long end = currentTime();
		System.out.println("the add method of " + list.getClass().getName() + " use time : " + (end - start) + "ms");
	}
	
	private static void testRandomAccess(List<String> list){
		long start = currentTime();
		for (int i = 0; i < list.size(); i++) {
			list.get(i);
		}
		long end = currentTime();
		System.out.println("the random access method of " + list.getClass().getName() + " use time : " + (end - start) + "ms");
	}
	
	private static void testIterator(List<String> list){
		long start = currentTime();
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			it.next();
		}
		long end = currentTime();
		System.out.println("the iterator method of " + list.getClass().getName() + " use time : " + (end - start) + "ms");
	}
	
	private static void testDelete(List<String> list){
		long start = currentTime();
		for (int i = 0; i < 10000; i++) {
			if(!list.isEmpty()){
				list.remove(0);
			}
		}
		long end = currentTime();
		System.out.println("the delete method of " + list.getClass().getName() + " use time : " + (end - start) + "ms");
	}
	
	private static long currentTime(){
		return System.currentTimeMillis();
	}
	/**
	 *  ArrayList 是线性表（数组）
		get() 直接读取第几个下标，复杂度 O(1)
		add(E) 添加元素，直接在后面添加，复杂度O（1）
		add(index, E) 添加元素，在第几个元素后面插入，后面的元素需要向后移动，复杂度O（n）
		remove（）删除元素，后面的元素需要逐个移动，复杂度O（n）
		
		LinkedList 是链表的操作
		get() 获取第几个元素，依次遍历，复杂度O(n)
		add(E) 添加到末尾，复杂度O(1)
		add(index, E) 添加第几个元素后，需要先查找到第几个元素，直接指针指向操作，复杂度O(n)
		remove（）删除元素，直接指针指向操作，复杂度O(1)
	 * @param args
	 */
	public static void main(String[] args) {
		testInsert();
		System.out.println("==========================================================");
		testRandomAccess();
		System.out.println("==========================================================");
		testIterator();
		System.out.println("==========================================================");
		testDelete();
	}
	
}
