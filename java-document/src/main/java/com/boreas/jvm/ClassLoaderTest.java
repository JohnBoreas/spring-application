package com.boreas.jvm;

/**
 * @author boreas
 * @create 2021-06-14 9:51
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("start");
            Person person = new Person();
            System.out.println("end");
        };

        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        Thread t2 = new Thread(runnable);
        t2.setName("t2");
        t1.start();
        t2.start();
    }

}
// 一个类只会被加载一次

class Person {
    static {
        if (true) {
            System.out.println("init");
            while (true) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}