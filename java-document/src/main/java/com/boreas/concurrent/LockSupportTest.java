package com.boreas.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport：一个线程阻塞工具类，所有的方法都是静态方法，可以让线程在任意位置阻塞，当然阻塞之后肯定得有唤醒的方法。
 * public static void park(Object blocker); // 暂停当前线程
 * public static void parkNanos(Object blocker, long nanos); // 暂停当前线程，不过有超时时间的限制
 * public static void parkUntil(Object blocker, long deadline); // 暂停当前线程，直到某个时间
 * public static void park(); // 无期限暂停当前线程
 * public static void parkNanos(long nanos); // 暂停当前线程，不过有超时时间的限制
 * public static void parkUntil(long deadline); // 暂停当前线程，直到某个时间
 * public static void unpark(Thread thread); // 恢复当前线程
 * public static Object getBlocker(Thread t);
 *
 * park和unpark可以实现类似wait和notify的功能，但是并不和wait和notify交叉，也就是说unpark不会对wait起作用，notify也不会对park起作用。
 * park和unpark的使用不会出现死锁的情况
 * blocker的作用是在dump线程的时候看到阻塞对象的信息
 * 
 * @author boreas
 * @create 2021-04-06 22:14
 */
public class LockSupportTest {

    public static void main(String[] args) {
        Thread threadPark = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("park start ..." + System.currentTimeMillis());
                /**
                 * park和unpark其实实现了wait和notify的功能，
                 * 差别:
                 * park不需要获取某个对象的锁
                 * 因为中断的时候park不会抛出InterruptedException异常，所以需要在park之后自行判断中断状态，然后做额外的处理
                 */
                LockSupport.park();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("park end ..." + System.currentTimeMillis());
            }
        });
        threadPark.setName("park");
        threadPark.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread threadUnPark = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("unpark start ..." + System.currentTimeMillis());
                // unpark调用时，如果当前线程还未进入park，则许可为true
                // park调用时，判断许可是否为true，如果是true，则继续往下执行；如果是false，则等待，直到许可为true
                LockSupport.unpark(threadPark);
                System.out.println("unpark end ..." + System.currentTimeMillis());
            }
        });
        threadUnPark.setName("unPark");
        threadUnPark.start();
    }
}
