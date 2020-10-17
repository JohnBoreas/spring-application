package com.boreas.designpatterns.singleton;

/**
 * 懒汉式+同步方法
 *
 * @author xuhua.jiang
 * @date 2020-10-12
 */
public class LazySynSingleton {

    // 懒汉式+同步方法
    // 既满足懒加载又百分百保证instance实例唯一性，但synchronized的排他性导致getInstance()只能在同一时刻被一个线程访问，性能低下
    private static LazySynSingleton instance = null;

    public static synchronized LazySynSingleton getInstance() {
        if (instance == null) {
            instance = new LazySynSingleton();
        }
        return instance;
    }
}
