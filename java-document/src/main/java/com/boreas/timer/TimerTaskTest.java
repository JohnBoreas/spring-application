package com.boreas.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * @author boreas
 * @create 2020-05-11 13:39
 */
public class TimerTaskTest {

    public static void main(String[] args) {

    }

    public void timer() {
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task1 run ...  execute time:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2 run ...  execute time:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Timer t = new Timer();
        t.schedule(task1, 0, 1000);
        t.schedule(task2, 0, 1000);
    }
}
