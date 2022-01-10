package com.boreas.jvm.classinit;

/**
 * @author boreas
 * @create 2022-01-06 16:07
 */
public class SubClass extends SuperClass {

    static {
        i = 0; // 给变量复制可以正常编译通过
//        System.out.print(i); // 这句编译器会提示“非法向前引用"
        System.out.println("SubClass init!");
    }
        static int i = 1;
}