package com.boreas.threadpool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author xuhua.jiang
 * @date 2020-7-07
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) {
        ForkJoinPoolTest test = new ForkJoinPoolTest();
        test.forkJoinPool();
        // 产生的是守护线程、后台线程，主线程不阻塞的话，看不到输出
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }

        System.out.println(Arrays.stream(nums).sum()); //stream api
    }

    public void forkJoinPool() {
        ForkJoinPool fjp = new ForkJoinPool();
        AddTask task = new AddTask(0, nums.length);
        fjp.execute(task);
        long result = task.join();
        System.out.println(result);
    }
     /*
	static class AddTask extends RecursiveAction {

		int start, end;

		AddTask(int s, int e) {
			start = s;
			end = e;
		}
		@Override
		protected void compute() {
			if(end-start <= MAX_NUM) {
				long sum = 0L;
				for(int i=start; i<end; i++) sum += nums[i];
				System.out.println("from:" + start + " to:" + end + " = " + sum);
			} else {
				int middle = start + (end-start)/2;
				AddTask subTask1 = new AddTask(start, middle);
				AddTask subTask2 = new AddTask(middle, end);
				subTask1.fork();
				subTask2.fork();
			}
		}
	}
	*/

    static class AddTask extends RecursiveTask<Long> {
        int start, end;
        AddTask(int s, int e) {
            start = s;
            end = e;
        }
        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }
            int middle = start + (end - start) / 2;
            AddTask subTask1 = new AddTask(start, middle);
            AddTask subTask2 = new AddTask(middle, end);
            subTask1.fork();
            subTask2.fork();
            return subTask1.join() + subTask2.join();
        }
    }

    public void workStealingPool() {
        // CPU是几核的就会启动几个线程，每一个线程都维护者自己的一个执行队列的
        ExecutorService service = Executors.newWorkStealingPool();// new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
        System.out.println(Runtime.getRuntime().availableProcessors());
        service.execute(new Runnable() {
            @Override
            public void run() {
                int time = 1000;
                try {
                    TimeUnit.MILLISECONDS.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(time  + " " + Thread.currentThread().getName());
            }
        });
    }
}
