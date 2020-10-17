package com.boreas.designpatterns.singleton;
/**
 * Holder方式
 * @author xuhua.jiang
 * @date 2020-10-12
 */
public class HolderSingleton {

    // Holder方式(最好的，使用广泛)
    // Holder方式借助了类加载的特点，在类初始化时不创建HolderSingleton实例，当Holder被主动应用时会创建HolderSingleton实例，
    // HolderSingleton实例的创建过程在java程序编译器收集到init()方法中，该方法又是同步方法可以保证内存的可见性、JVM指令的顺序性和原子性
    private HolderSingleton(){};

    private static class Holder {
        private static HolderSingleton instance = new HolderSingleton();
    }

    public static HolderSingleton getInstance() {
        return Holder.instance;
    }
}
