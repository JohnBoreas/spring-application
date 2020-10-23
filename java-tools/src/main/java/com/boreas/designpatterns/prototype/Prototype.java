package com.boreas.designpatterns.prototype;

/**
 * 抽象原型
 */
public interface Prototype {

    Object clone() throws CloneNotSupportedException;
}