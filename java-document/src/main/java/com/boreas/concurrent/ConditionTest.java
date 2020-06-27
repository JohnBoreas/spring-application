package com.boreas.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author boreas
 * @create 2020-06-14 21:45
 */
public class ConditionTest {

    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public static void main(String[] args) {
        ConditionTest useCase = new ConditionTest();
        useCase.objectTest();
    }

    public void conditionTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                conditionWait();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                conditionSignal();
            }
        });
        executorService.shutdown();
    }


    public void conditionWait() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "拿到锁了");
            System.out.println(Thread.currentThread().getName() + "等待信号");
            // await()方法后，当前线程会释放锁并在此等待
            condition.await();
            System.out.println(Thread.currentThread().getName() + "拿到信号");
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() {
        lock.lock();
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "拿到锁了");
            // 其他线程signal()方法，通知当前线程后，当前线程才从await()方法返回，并且在返回前已经获取了锁
            condition.signal();
            System.out.println(Thread.currentThread().getName() + "发出信号");
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public void objectTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                objectWait();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                objectNotify();
            }
        });
        executorService.shutdown();
    }

    public void objectWait() {
        synchronized (this) {
            try {
                System.out.println(Thread.currentThread().getName() + "拿到锁了");
                System.out.println(Thread.currentThread().getName() + "等待信号");
                this.wait();
                System.out.println(Thread.currentThread().getName() + "拿到信号");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void objectNotify() {
        synchronized (this) {
            try {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + "拿到锁了");
                this.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
