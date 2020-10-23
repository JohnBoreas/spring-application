package com.boreas.designpatterns.prototype;

import java.io.Serializable;
import java.util.Map;

public class ConcretePrototypeB implements Cloneable, Serializable {

    public Map<String, String> map;

    public String name;

    public ConcretePrototypeA concretePrototype;

    ConcretePrototypeB(String name, Map<String, String> map, ConcretePrototypeA concretePrototype){
        this.name = name;
        this.map = map;
        this.concretePrototype = concretePrototype;
    }

    public Object clone() throws CloneNotSupportedException {
        ConcretePrototypeB object = (ConcretePrototypeB) super.clone();
        object.concretePrototype = (ConcretePrototypeA) concretePrototype.clone();
        return object;
    }

    public String toString() {
        String mapStr = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            mapStr = mapStr + entry.getKey() + "[" + entry.getValue() + "];";
        }
        return mapStr + "--" + name + "--" + concretePrototype.name + "--" + concretePrototype.length;
    }
}
