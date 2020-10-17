package com.boreas.designpatterns.singleton;

/**
 * 懒加载模式
 * @author xuhua.jiang
 * @date 2020-10-12
 */
public class LazySingleton {

    // 懒加载模式
    // 在使用类实例的时候再创建，避免类初始化时提前创建，但会导致多线程环境中被多次初始化，无法保证实例的唯一性
    private static LazySingleton instance = null;

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
