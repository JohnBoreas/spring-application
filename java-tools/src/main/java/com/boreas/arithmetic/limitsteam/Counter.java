package com.boreas.arithmetic.limitsteam;

/**
 * 计数器
 * @author boreas
 * @create 2022-06-06 14:36
 */
public class Counter {

    public long startTime = System.currentTimeMillis(); // 当前时间
    public int reqCount = 0; // 初始化计数器
    public final int limit = 100; // 时间窗口内最大请求数
    public final long interval = 1000 * 60; // 时间窗口ms

    public boolean limit() {
        long now = System.currentTimeMillis();
        if (now - startTime < interval) {
            reqCount ++;
            // 判断当前时间窗口内是否超过最大请求控制数
            return reqCount <= limit;
        } else {
            startTime = now;
            reqCount = 1;
            return true;
        }
    }
}
