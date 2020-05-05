package com.boreas.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * @author boreas
 * @create 2020-04-13 17:39
 */
public class ExchangerTest {
    /**
     * Exchanger : 用于两个线程之间进行数据交换，线程会堵塞在exchange()，直到另一个线程也同时到达该方法
      */
    public  void exchangerTest() {
        final Exchanger<List<Integer>> exchanger = new Exchanger<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Integer> l = new ArrayList<>(2);
                l.add(1);
                l.add(2);
                try {
                    l = exchanger.exchange(l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread1" + l);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Integer> l = new ArrayList<>(2);
                l.add(4);
                l.add(5);
                try {
                    l = exchanger.exchange(l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread2" + l);
            }
        }).start();
    }
}
