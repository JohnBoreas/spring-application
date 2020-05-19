package com.boreas.thread;

/**
 * @author boreas
 * @create 2020-05-19 22:20
 */
public class CacheLinePadDing {

    private static class PadDing {
        public volatile long p1, p2, p3, p4, p5, p6, p7;
    }

    private static class T extends PadDing {
        public volatile long x = 0;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }
}
