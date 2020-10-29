package com.boreas.designpatterns.decorator;

/**
 * 具体装饰者A
 */
public class ConcreteDecoratorA extends Decorator {

    @Override
    public void operationA() {
        super.operationA();
        operationD();
    }

    // operationD
    public void operationD() {
        System.out.println("ConcreteDecoratorA operationD");
    }
}
