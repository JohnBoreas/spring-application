package com.boreas.concurrent.exchanger;

import java.util.concurrent.Exchanger;

/**
 */
public class ExChangerDemo {
    static Exchanger<String> ec = new Exchanger<>();
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ownData = "毒品";
                System.out.println(Thread.currentThread().getName()+"准备交易出去:"+ownData);
                try {
                    String changeData = ec.exchange(ownData);
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+"交易回来:"+changeData);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"贩毒者").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String ownData = "美元";
                System.out.println(Thread.currentThread().getName()+"准备交易出去:"+ownData);
                try {
                    String changeData = ec.exchange(ownData);
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"交易回来："+changeData);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"吸毒者").start();
    }
}

