package com.boreas.jvm;

import java.util.ArrayList;

/**
 * @author boreas
 * @create 2020-05-13 15:18
 */
public class OverStackOrHeap {
    /**
     * java.lang.OutOfMemoryError: Java heap space
     */
    public void heap() {
        ArrayList list = new ArrayList();
        while (true) {
            list.add(new OverStackOrHeap());
        }
    }
    /**
     * java.lang.StackOverflowError
     */
    public void stack(int i) {
        stack(i);
        i ++;
    }


    public static void main(String[] args) {
        OverStackOrHeap heap = new OverStackOrHeap();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OverStackOrHeap heap = new OverStackOrHeap();
                int i = 0;
                heap.stack(i);
                System.out.println(i);
            }
        });
        thread.start();
        try {
            Thread.sleep(10000);
            System.out.println("1111111111111");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
