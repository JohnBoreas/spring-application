package com.boreas.jvm;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author boreas
 * @create 2020-05-13 15:18
 */
public class OverStackOrHeap {
    /**
     * java.lang.OutOfMemoryError: Java heap space
     */
    public void heap() {
        ArrayList list = new ArrayList();
        while (true) {
            list.add(new OverStackOrHeap());
        }
    }
    /**
     * java.lang.StackOverflowError
     */
    public void stack() {
        stack();
    }
}
