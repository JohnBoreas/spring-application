package com.boreas.designpatterns.decorator;

/**
 * 具体被装饰对象
 */
public class ConcreteComponent implements Component {

    public void operationA() {
        System.out.println("operationA");
    }

    public void operationB() {
        System.out.println("operationB");
    }

    public void operationC() {
        System.out.println("operationC");
    }
}
