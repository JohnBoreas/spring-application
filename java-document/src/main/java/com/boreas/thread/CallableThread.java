package com.boreas.thread;

import java.util.concurrent.*;

/**
 * 创建线程, 实现Callable接口
 * 扩展性好
 * 支持多线程处理同一份资源
 * 具备返回值以及可以抛出受检查异常
 */
public class CallableThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(50000);
        return "Callable";
    }

    public static void main(String[] args){
        // 线程执行目标
        CallableThread callableThread = new CallableThread();
        // 包装线程执行目标，因为Thread的构造函数只能接受Runnable接口的实现类，而FutureTask类实现了Runnable接口
        FutureTask<String> futureTask = new FutureTask<>(callableThread);
        // 传入线程执行目标，实例化线程对象
        Thread thread = new Thread(futureTask);
        // 启动线程
        thread.start();
        String result = null;
        try {
            // 获取线程执行结果
//            result = futureTask.get();// 阻塞获取
            result = futureTask.get(2, TimeUnit.SECONDS);// 超时，但是当前线程不结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(thread.isAlive());
        System.out.println(result);
    }
}