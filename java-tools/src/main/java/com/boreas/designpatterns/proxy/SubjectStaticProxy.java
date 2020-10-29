package com.boreas.designpatterns.proxy;

/**
 *  代理类
 */
public class SubjectStaticProxy implements Subject {

    private Subject subject;

    public SubjectStaticProxy(Subject subject) {
        this.subject = subject;
    }

    public void request() {
        System.out.println("SubjectStaticProxy start");
        subject.request();
        System.out.println("SubjectStaticProxy end");
    }
}