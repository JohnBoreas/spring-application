package com.boreas.threadpool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.LongStream;

/**
 * @author xuhua.jiang
 * @date 2020-7-07
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) {
        ForkJoinPoolTest test = new ForkJoinPoolTest();
        test.forkJoinTest();
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

    public void forkJoinTest() {
        //ForkJoin实现
        long l = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();//实现ForkJoin 就必须有ForkJoinPool的支持
        ForkJoinTask<Long> task = new ForkJoinWork(0L, 10000000000L);//参数为起始值与结束值
        Long invoke = forkJoinPool.invoke(task);
        long l1 = System.currentTimeMillis();
        System.out.println("invoke = " + invoke + "  time: " + (l1 - l));
        //invoke = -5340232216128654848  time: 56418
        //ForkJoinWork forkJoinWork = new ForkJoinWork(0L, 10000000000L);
    }

    public void forEachTest() {
        //普通线程实现
        Long x = 0L;
        Long y = 10000000000L;
        long l = System.currentTimeMillis();
        for (Long i = 0L; i <= y; i++) {
            x += i;
        }
        long l1 = System.currentTimeMillis();
        System.out.println("invoke = " + x + "  time: " + (l1 - l));
        //invoke = -5340232216128654848  time: 64069
    }

    public void longStreamTest() {
        // Java 8 并行流的实现
        long l = System.currentTimeMillis();
        long reduce = LongStream.rangeClosed(0, 10000000000L).parallel().reduce(0, Long::sum);
        // long reduce = LongStream.rangeClosed(0, 10000000000L).parallel().reduce(0, (a, b) -> a+b);
        long l1 = System.currentTimeMillis();
        System.out.println("invoke = " + reduce + "  time: " + (l1 - l));

        // invoke = -5340232216128654848  time: 2152
    }
}
