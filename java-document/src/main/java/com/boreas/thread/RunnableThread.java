package com.boreas.thread;

/**
 * 实现Runnable接口
 * 扩展性好
 * 支持多线程共享资源的场景
 */
public class RunnableThread implements Runnable {
    //线程体
    @Override
    public void run() {
        System.out.println("RunnableThread");
        while (true) {
            try {
                Thread.sleep(30111);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        // 线程的执行目标对象
        RunnableThread runnableThread = new RunnableThread();
        // 实际的线程对象
        Thread thread = new Thread(runnableThread);
        // 启动线程
        thread.start();
        System.out.println(thread.getState().name());
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.notify();
        System.out.println(System.currentTimeMillis() + " end " + thread.isInterrupted() + ", " + thread.isAlive());
    }
}
