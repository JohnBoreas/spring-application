package com.boreas.designpatterns.singleton;
/**
 * DCL
 * Volatile+Double-Check
 * @author xuhua.jiang
 * @date 2020-10-12
 */
public class LazyVolatileSingleton {

    // Volatile+Double-Check
    // 可满足多线程下的单例、懒加载以及获得实例的高效性
    private volatile static LazyVolatileSingleton instance = null;

    public static LazyVolatileSingleton getInstance() {
        if (instance == null) {
            synchronized(LazyVolatileSingleton.class) {
                if (null == instance) {
                    // 创建对象分多步:
                    // （1）memory = allocate();   分配对象的内存空间
                    // （2）ctorInstance(memory);  初始化对象
                    // （3）instance = memory;     设置instance指向刚分配的内存地址
                    // 重排序是（2）（3）发生
                    instance = new LazyVolatileSingleton();
                }
            }
        }
        return instance;
    }
}
