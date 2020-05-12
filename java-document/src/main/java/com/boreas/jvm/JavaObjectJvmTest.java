package com.boreas.jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author boreas
 * @create 2020-05-11 22:27
 */
public class JavaObjectJvmTest {
    public static void main(String[] args) {
        JavaObjectJvmTest test = new JavaObjectJvmTest();
        test.javaObjectJvm();
    }

    public void javaObjectJvm() {
        Object object = new Integer(11);
        // 打印object的内存布局
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        // 将object对象锁住后再打印其内存布局
        synchronized (object) {
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }
    }
}
