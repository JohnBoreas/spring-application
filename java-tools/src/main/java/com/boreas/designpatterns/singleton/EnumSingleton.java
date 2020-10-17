package com.boreas.designpatterns.singleton;
/**
 * 枚举方式
 * @author xuhua.jiang
 * @date 2020-10-12
 */
public enum EnumSingleton {
    // 枚举方式
    // 枚举不允许被继承，线程安全，且只能被实例化一次，但不能懒加载
    INSTANCE;
    EnumSingleton() {
        // 初始化;
    }
    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
