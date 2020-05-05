package com.boreas.container;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @author boreas
 * @create 2020-03-22 下午 12:28
 */
public class ListPerformsTest {

    public static void main(String[] args) {
        ListPerformsTest test = new ListPerformsTest();
        test.add();
    }
    /**
     * test add() function
     * cap:2 << 20, size:2 << 19
     * java.util.ArrayList: use time : 118, used : 63
     * java.util.ArrayList: use time : 36, used : 74
     * java.util.Vector: use time : 65, used : 83
     * java.util.Vector: use time : 39, used : 57
     * java.util.LinkedList: use time : 1910, used : 80
     *
     * cap:2 << 23, size:2 << 21
     * java.util.ArrayList: use time : 1878: for time : 47, used : 286
     * java.util.ArrayList: use time : 2411: for time : 13, used : 224
     * java.util.Vector: use time : 3619: for time : 111, used : 244
     * java.util.Vector: use time : 1611: for time : 94, used : 224
     * java.util.LinkedList: use time : 4559: for time : 64, used : 320
     *
     * 遍历的话基本速度差不了太多，顺序插入基本也相差不大，Link会稍微大点，随机删除Link占优，随机访问数组占优
     */
    public void add() {
        long startTime = System.currentTimeMillis();
        List<String> arrayList = new ArrayList<>();
        List<String> arrayCapList = new ArrayList<>(2 << 23);
        List<String> vectorList = new Vector<>();
        List<String> vectorCapList = new Vector<>(2 << 23);
        List<String> linkedList = new LinkedList<>();
        System.out.println("current time : " + (System.currentTimeMillis() - startTime));
        add(arrayList, 2 << 21);
        add(arrayCapList, 2 << 21);
        add(vectorList, 2 << 21);
        add(vectorCapList, 2 << 21);
        add(linkedList, 2 << 21);
    }

    public void add(List<String> list, int size) {
        Long fm = Runtime.getRuntime().freeMemory();// Java 虚拟机中的空闲内存量。
        Long tm = Runtime.getRuntime().totalMemory();// Java 虚拟机中的内存总量。
        Long mm = Runtime.getRuntime().maxMemory();// Java 虚拟机试图使用的最大内存量。
        Long temp=(tm - fm);// start use memory
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add("a" + i);
        }
        long useTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        for (String str : list) {}
        long forTime = System.currentTimeMillis() - startTime;
        fm = Runtime.getRuntime().freeMemory();// Java 虚拟机中的空闲内存量。
        tm = Runtime.getRuntime().totalMemory();// Java 虚拟机中的内存总量。
        mm = Runtime.getRuntime().maxMemory();// Java 虚拟机试图使用的最大内存量。
        System.out.println(list.getClass().getName() + ": use time : " + useTime + ": for time : " + forTime + ", used : " + (tm - fm - temp) / 1024 / 1024);
        System.gc();
    }


}
