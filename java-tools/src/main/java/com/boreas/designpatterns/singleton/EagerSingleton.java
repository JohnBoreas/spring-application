package com.boreas.designpatterns.singleton;

/**
 * 饿汉模式
 * @author xuhua.jiang
 */
public class EagerSingleton {
    // 饿汉模式
    // 适用于一个类中的成员属性较少，且占用的内存资源不多的场景
    // 饿汉式的单例设计模式可以保证多个线程下的唯一实例，getInstance方法性能也比较高，但是无法进行懒加载
    private EagerSingleton(){}

    private static EagerSingleton instance = new EagerSingleton();

    public static EagerSingleton getInstance() {
        return instance;
    }
}
