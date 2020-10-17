package com.boreas.designpatterns.singleton;
/**
 * Double-Check
 * @author xuhua.jiang
 * @date 2020-10-12
 */
public class LazyDoubleCheckSingleton {

    // Double-Check
    // 既满足懒加载，也保证了instance实例唯一性，也提供了高效的数据同步策略，
    // 但是在多线程情况下优肯引起空指针异常（JVM运行时指令重排和Happens-Before规则，conn和instance之间的实例化顺序并无前后关系的约束，可能instance先被实例化，而conn和socket并未完成实例化，导致使用conn时null异常）
    private static LazyDoubleCheckSingleton instance = null;

    public static LazyDoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized(LazyDoubleCheckSingleton.class) {
                if (null == instance) {
                    // 指令重排序引起:指令重排序是为了优化指令，提高程序运行效率。
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
