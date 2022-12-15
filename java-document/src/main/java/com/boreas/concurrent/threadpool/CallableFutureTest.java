package com.boreas.concurrent.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 */
public class CallableFutureTest {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = exec.submit(task);
        exec.shutdown();
        try{
            Thread.sleep(1000);
            System.out.println("主线程在执行任务...");
            System.out.println("task运行结构:"+result.get());
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("所有任务执行完毕....");
    }
}
class Task implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("线程正在进行计算....");
        Thread.sleep(3000);
        int sum = 0;
        for(int i = 0;i <= 100;i++){
            sum += i;
        }
        return sum;
    }
}