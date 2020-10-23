package com.boreas.designpatterns.prototype;

import java.io.Serializable;

/**
 * 具体原型（Concrete Prototype）
 */
public class ConcretePrototypeA implements Cloneable, Serializable {

    public String name;
    public double length;

    ConcretePrototypeA(String name, double length){
        this.name = name;
        this.length = length;
    }

    public Object clone() throws CloneNotSupportedException {
        ConcretePrototypeA object = (ConcretePrototypeA) super.clone();
        return object;
    }

    public String toString() {
        return name + ", " + length;
    }
}
