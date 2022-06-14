package com.boreas.arithmetic.limitsteam;

/**
 * @author boreas
 * @create 2022-06-06 15:32
 */
public class LeakyBucket {

    public long startTime = System.currentTimeMillis();// 起始时间
    public long capacity; // 桶的容量
    public long rate; // 水漏出的速度(每秒系统能处理的请求数)
    public long water; // 当前水量(当前累积请求数)

    public boolean limit() {
        long now = System.currentTimeMillis();
        // 先执行 漏水，计算剩余水量
        water = Math.max(0, water - (now - startTime) / 1000 * rate);
        startTime = now;
        if ((water + 1) < capacity) {
            // 尝试加水,并且水还未满
            water += 1;
            return true;
        } else {
            // 水满，拒绝加水
            return false;
        }
    }
}
