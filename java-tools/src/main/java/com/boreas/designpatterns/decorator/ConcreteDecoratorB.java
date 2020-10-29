package com.boreas.designpatterns.decorator;

/**
 * 具体装饰者B
 */
public class ConcreteDecoratorB extends Decorator {

    @Override
    public void operationB() {
        super.operationB();
        operationE();
    }

    // operationE
    public void operationE() {
        System.out.println("ConcreteDecoratorB operationE");
    }
}
