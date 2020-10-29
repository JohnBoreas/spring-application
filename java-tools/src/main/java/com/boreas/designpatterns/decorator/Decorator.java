package com.boreas.designpatterns.decorator;

/**
 * 装饰者抽象类
 */
public class Decorator implements Component {

    private ConcreteComponent component;

    public void setComponent(ConcreteComponent component) {
        this.component = component;
    }

    public void operationA() {
        System.out.println("Decorator operationA");
        component.operationA();
    }

    public void operationB() {
        System.out.println("Decorator operationB");
        component.operationB();
    }

    public void operationC() {
        System.out.println("Decorator operationC");
        component.operationC();
    }
}
