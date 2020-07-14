package com.boreas.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  @desc java集合中Fast-Fail的测试程序。
 *
 *   fast-fail事件产生的条件：当多个线程对Collection进行操作时，若其中某一个线程通过iterator去遍历集合时，
 *   该集合的内容被其他线程所改变；则会抛出ConcurrentModificationException异常。
 *   fast-fail解决办法：通过util.concurrent集合包下的相应类去处理，则不会产生fast-fail事件。
 *
 *   本例中，分别测试ArrayList和CopyOnWriteArrayList这两种情况。ArrayList会产生fast-fail事件，
 *   而CopyOnWriteArrayList不会产生fast-fail事件。
 *   (01) 使用ArrayList时，会产生fast-fail事件，抛出ConcurrentModificationException异常；定义如下：
 *            private static List<String> list = new ArrayList<String>();
 *   (02) 使用时CopyOnWriteArrayList，不会产生fast-fail事件；定义如下：
 *            private static List<String> list = new CopyOnWriteArrayList<String>();
 *
 * @author Lenovo
 * 2017年11月7日下午9:59:11
 */
public class FailFastTest {
	/**
	 * list 的迭代器 Fail-Fast
	 */
	private static List<Integer> list = new ArrayList<>();
	//private static List<String> list = new CopyOnWriteArrayList<String>();

	/**
	 * @desc:线程one迭代list
	 * @Project:test
	 * @file:FailFastTest.java
	 * @Authro:chenssy
	 * @data:2014年7月26日
	 */
	private static class threadOne extends Thread {
		public void run() {
			Iterator<Integer> iterator = list.iterator();
			while (iterator.hasNext()) {
				int i = iterator.next();
				System.out.println("ThreadOne 遍历:" + i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @desc:当i == 3时，修改list
	 * @Project:test
	 * @file:FailFastTest.java
	 * @Authro:chenssy
	 * @data:2014年7月26日
	 */
	private static class threadTwo extends Thread {
		public void run() {
			int i = 0;
			while (i < 6) {
				System.out.println("ThreadTwo run：" + i);
				if (i == 3) {
					list.remove(i);
				}
				i++;
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		new threadOne().start();
		new threadTwo().start();
	}
}
