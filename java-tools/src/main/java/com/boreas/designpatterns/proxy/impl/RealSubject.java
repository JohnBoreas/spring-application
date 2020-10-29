package com.boreas.designpatterns.proxy.impl;

import com.boreas.designpatterns.proxy.Subject;

/**
 * 真实主题类
 */
public class RealSubject implements Subject {

    public void request() {
        System.out.println("request");
    }
}
